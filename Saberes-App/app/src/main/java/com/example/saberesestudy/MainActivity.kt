package com.example.saberesestudy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saberesestudy.util.ApiClient
import com.example.saberesestudy.model.LoginRequest
import com.example.saberesestudy.model.LoginResponse
import com.example.saberesestudy.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // ⚠️ Essas variáveis estavam faltando no seu erro
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var btnCadastrar: Button

    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se já estiver logado, pula o login
//        session.getUsuario()?.let {
//            startActivity(Intent(this, ConteudosActivity::class.java))
//            finish()
//            return
//        }

        // IDs do XML (activity_main.xml)
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        btnCadastrar = findViewById(R.id.btnCadastrar)

        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val senha = edtSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha e-mail e senha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fazerLogin(email, senha)
        }

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        val req = LoginRequest(email = email, senha = senha)

        ApiClient.api.login(req).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(
                        this@MainActivity,
                        "Erro de resposta do servidor.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val body = response.body()!!
                if (body.sucesso && body.usuario != null) {
                    session.salvarUsuario(body.usuario)

                    Toast.makeText(
                        this@MainActivity,
                        "Bem-vindo, ${body.usuario.nome}",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this@MainActivity, ConteudosActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        body.erro ?: "E-mail ou senha inválidos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Falha de conexão com o servidor.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
