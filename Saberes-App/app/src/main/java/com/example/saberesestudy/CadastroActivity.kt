package com.example.saberesestudy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class CadastroActivity : AppCompatActivity() {

    private lateinit var edtNomeCadastro: EditText
    private lateinit var edtSerieCadastro: EditText
    private lateinit var edtEmailCadastro: EditText
    private lateinit var edtSenhaCadastro: EditText
    private lateinit var btnSalvarCadastro: Button
    private lateinit var btnVoltarLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        edtNomeCadastro = findViewById(R.id.edtNomeCadastro)
        edtSerieCadastro = findViewById(R.id.edtSerieCadastro)
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro)
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro)
        btnSalvarCadastro = findViewById(R.id.btnSalvarCadastro)
        btnVoltarLogin = findViewById(R.id.btnVoltarLogin)

        btnSalvarCadastro.setOnClickListener {
            val nome = edtNomeCadastro.text.toString().trim()
            val serie = edtSerieCadastro.text.toString().trim()
            val email = edtEmailCadastro.text.toString().trim()
            val senha = edtSenhaCadastro.text.toString().trim()

            if (nome.isEmpty() || serie.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                cadastrarAluno(nome, serie, email, senha)
            }
        }

        btnVoltarLogin.setOnClickListener {
            finish() // volta para MainActivity
        }
    }

    private fun cadastrarAluno(nome: String, serie: String, email: String, senha: String) {
        // roda em thread separada para não travar a UI
        Thread {
            try {
                val url = URL("http://10.0.2.2/saberes/api/cadastrar_aluno.php")

                val postData = StringBuilder()
                postData.append("nome=").append(URLEncoder.encode(nome, "UTF-8"))
                postData.append("&serie=").append(URLEncoder.encode(serie, "UTF-8"))
                postData.append("&email=").append(URLEncoder.encode(email, "UTF-8"))
                postData.append("&senha=").append(URLEncoder.encode(senha, "UTF-8"))

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

                val out = BufferedWriter(OutputStreamWriter(conn.outputStream, "UTF-8"))
                out.write(postData.toString())
                out.flush()
                out.close()

                val responseCode = conn.responseCode
                val responseBuilder = StringBuilder()

                val reader = if (responseCode in 200..299) {
                    conn.inputStream
                } else {
                    conn.errorStream
                }?.bufferedReader()

                reader?.use { br ->
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        responseBuilder.append(line)
                    }
                }

                val responseString = responseBuilder.toString()

                runOnUiThread {
                    try {
                        val json = JSONObject(responseString)
                        if (json.optBoolean("sucesso", false)) {
                            Toast.makeText(
                                this,
                                "Cadastro realizado com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                            finish() // volta para login
                        } else {
                            val msg = json.optString("erro", "Falha ao cadastrar.")
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            "Erro no retorno do servidor.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                conn.disconnect()

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Erro ao conectar com o servidor.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }
}
