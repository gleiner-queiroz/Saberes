package com.example.saberesestudy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saberesestudy.model.Conteudo
import com.example.saberesestudy.util.ApiClient
import com.example.saberesestudy.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilActivity : AppCompatActivity() {

    private lateinit var txtNome: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtSerie: TextView
    private lateinit var rvFavoritos: RecyclerView
    private lateinit var btnVoltar: Button
    private lateinit var btnLogout: Button

    private lateinit var adapterFavoritos: ConteudoAdapter
    private val listaFavoritos = mutableListOf<Conteudo>()

    private val session by lazy { SessionManager(this) }
    private val api get() = ApiClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        txtNome = findViewById(R.id.txtNome)
        txtEmail = findViewById(R.id.txtEmail)
        txtSerie = findViewById(R.id.txtSerie)
        rvFavoritos = findViewById(R.id.rvFavoritos)
        btnVoltar = findViewById(R.id.btnVoltar)
        btnLogout = findViewById(R.id.btnLogoutPerfil)

        // Dados do aluno da sessão
        val usuario = session.getUsuario()
        if (usuario == null) {
            Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        txtNome.text = usuario.nome
        txtEmail.text = usuario.email
        txtSerie.text = if (!usuario.serie.isNullOrBlank()) {
            "Série: ${usuario.serie}"
        } else {
            "Série: não informada"
        }

        // RecyclerView de favoritos
        adapterFavoritos = ConteudoAdapter(listaFavoritos) { conteudo ->
            val intent = Intent(this, ConteudoDetalheActivity::class.java)
            intent.putExtra("conteudo_id", conteudo.id)
            startActivity(intent)
        }
        rvFavoritos.layoutManager = LinearLayoutManager(this)
        rvFavoritos.adapter = adapterFavoritos

        // Botão voltar para a lista de conteúdos
        btnVoltar.setOnClickListener {
            finish()
        }

        // Logout via perfil
        btnLogout.setOnClickListener {
            session.limpar()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Carregar favoritos do servidor
        carregarFavoritos(usuario.id)
    }

    private fun carregarFavoritos(alunoId: Int) {
        api.listarFavoritos(alunoId).enqueue(object : Callback<List<Conteudo>> {
            override fun onResponse(
                call: Call<List<Conteudo>>,
                response: Response<List<Conteudo>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val dados = response.body()!!
                    listaFavoritos.clear()
                    listaFavoritos.addAll(dados)
                    adapterFavoritos.notifyDataSetChanged()

                    if (dados.isEmpty()) {
                        Toast.makeText(
                            this@PerfilActivity,
                            "Você ainda não favoritou nenhum conteúdo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@PerfilActivity,
                        "Erro ao carregar favoritos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Conteudo>>, t: Throwable) {
                Toast.makeText(
                    this@PerfilActivity,
                    "Falha na conexão ao carregar favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
