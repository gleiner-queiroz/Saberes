package com.example.saberesestudy

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saberesestudy.model.Conteudo
import com.google.android.material.button.MaterialButton
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class ConteudosActivity : AppCompatActivity() {

    private lateinit var rvConteudos: RecyclerView
    private lateinit var edtBusca: EditText
    private lateinit var edtMateria: EditText
    private lateinit var edtSerie: EditText
    private lateinit var btnFiltrar: MaterialButton
    private lateinit var btnPerfil: MaterialButton
    private lateinit var btnLogout: MaterialButton

    private lateinit var adapter: ConteudoAdapter
    private val listaConteudos = mutableListOf<Conteudo>()

    // Emulador acessando XAMPP no PC
    private val baseUrl = "http://10.0.2.2/saberes/api"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conteudos)

        // Liga com os IDs do XML
        rvConteudos = findViewById(R.id.rvConteudos)
        edtBusca = findViewById(R.id.edtBusca)
        edtMateria = findViewById(R.id.edtMateria)
        edtSerie = findViewById(R.id.edtSerie)
        btnFiltrar = findViewById(R.id.btnFiltrar)
        btnPerfil = findViewById(R.id.btnPerfil)
        btnLogout = findViewById(R.id.btnLogout)

        // RecyclerView
        adapter = ConteudoAdapter(listaConteudos) { conteudo ->
            val intent = Intent(this, ConteudoDetalheActivity::class.java)
            // usa SEMPRE a MESMA chave:
            intent.putExtra("conteudo_id", conteudo.id)
            startActivity(intent)
        }

        rvConteudos.layoutManager = LinearLayoutManager(this)
        rvConteudos.adapter = adapter

        // Botão filtrar
        btnFiltrar.setOnClickListener {
            val materia = edtMateria.text.toString().trim()
            val serie = edtSerie.text.toString().trim()
            carregarConteudos(materia, serie)
        }


        // Botão perfil
        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        // Botão logout
        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
            prefs.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Carrega conteúdos iniciais (sem filtro)
        carregarConteudos("", "")
    }

    private fun carregarConteudos(materia: String, serie: String) {
        val client = OkHttpClient()

        val urlBuilder = HttpUrl.parse("$baseUrl/listar_conteudos.php")!!.newBuilder()
        if (materia.isNotEmpty()) urlBuilder.addQueryParameter("materia", materia)
        if (serie.isNotEmpty()) urlBuilder.addQueryParameter("serie", serie)
        val url = urlBuilder.build().toString()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@ConteudosActivity,
                        "Erro ao carregar conteúdos: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                if (!response.isSuccessful || body.isNullOrEmpty()) {
                    runOnUiThread {
                        Toast.makeText(
                            this@ConteudosActivity,
                            "Erro na resposta da API.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return
                }

                try {
                    val jsonArray = JSONArray(body)

                    listaConteudos.clear()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)

                        val conteudo = Conteudo(
                            id = obj.getInt("id"),
                            titulo = obj.getString("titulo"),
                            materia = obj.getString("materia"),
                            serie = obj.getString("serie")
                        )

                        listaConteudos.add(conteudo)
                    }

                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@ConteudosActivity,
                            "Erro ao processar dados.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }
}
