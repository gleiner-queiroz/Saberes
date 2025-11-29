package com.example.saberesestudy

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saberesestudy.model.*
import com.example.saberesestudy.util.ApiClient
import com.example.saberesestudy.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConteudoDetalheActivity : AppCompatActivity() {

    private var conteudoId: Int = 0
    private var professorId: Int = 0
    private var favoritoAtual: Boolean = false

    private lateinit var txtTitulo: TextView
    private lateinit var txtInfo: TextView
    private lateinit var txtTexto: TextView
    private lateinit var txtLinks: TextView
    private lateinit var btnFavorito: Button
    private lateinit var edtMensagem: EditText
    private lateinit var btnEnviar: Button
    private lateinit var rvMensagens: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    private val session by lazy { SessionManager(this) }
    private val api get() = ApiClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conteudo_detalhe)

        // Garante que pega o ID certo, independente da chave usada
        conteudoId = intent.getIntExtra("conteudo_id", 0)
        if (conteudoId == 0) {
            conteudoId = intent.getIntExtra("id_conteudo", 0)
        }

        if (conteudoId == 0) {
            Toast.makeText(this, "Conteúdo inválido.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        txtTitulo = findViewById(R.id.txtTitulo)
        txtInfo = findViewById(R.id.txtInfo)
        txtTexto = findViewById(R.id.txtTexto)
        txtLinks = findViewById(R.id.txtLinks)
        btnFavorito = findViewById(R.id.btnFavorito)
        edtMensagem = findViewById(R.id.edtMensagem)
        btnEnviar = findViewById(R.id.btnEnviar)
        rvMensagens = findViewById(R.id.rvMensagens)

        rvMensagens.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(emptyList())
        rvMensagens.adapter = chatAdapter

        btnFavorito.setOnClickListener { toggleFavorito() }
        btnEnviar.setOnClickListener { enviarMensagem() }

        // Carrega dados
        carregarDetalhe()
        carregarMensagens()
        verificarStatusFavorito()  // <<< AQUI: verificação inicial
    }

    // ================== DETALHE DO CONTEÚDO ==================

    private fun carregarDetalhe() {
        api.conteudoDetalhe(conteudoId).enqueue(object : Callback<ConteudoDetalhe> {
            override fun onResponse(
                call: Call<ConteudoDetalhe>,
                response: Response<ConteudoDetalhe>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val c = response.body()!!
                    professorId = c.professor_id
                    txtTitulo.text = c.titulo
                    txtInfo.text = "${c.materia} • ${c.serie} • ${c.professor_nome}"
                    txtTexto.text = c.texto ?: ""

                    val linksString = buildString {
                        if (!c.imagens.isNullOrBlank()) append("Imagens: ${c.imagens}\n")
                        if (!c.videos.isNullOrBlank()) append("Vídeos: ${c.videos}\n")
                        if (!c.links.isNullOrBlank()) append("Links: ${c.links}")
                    }
                    txtLinks.text = linksString
                } else {
                    Toast.makeText(
                        this@ConteudoDetalheActivity,
                        "Erro ao carregar conteúdo.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ConteudoDetalhe>, t: Throwable) {
                Toast.makeText(
                    this@ConteudoDetalheActivity,
                    "Falha na conexão.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // ================== CHAT ==================

    private fun carregarMensagens() {
        val aluno = session.getUsuario() ?: return

        api.listarMensagens(conteudoId, aluno.id)
            .enqueue(object : Callback<List<Mensagem>> {
                override fun onResponse(
                    call: Call<List<Mensagem>>,
                    response: Response<List<Mensagem>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val lista = response.body()!!
                        chatAdapter.atualizar(lista)
                        if (lista.isNotEmpty()) {
                            rvMensagens.scrollToPosition(lista.size - 1)
                        }
                    } else {
                        Toast.makeText(
                            this@ConteudoDetalheActivity,
                            "Erro ao carregar chat.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Mensagem>>, t: Throwable) {
                    Toast.makeText(
                        this@ConteudoDetalheActivity,
                        "Falha na conexão.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun enviarMensagem() {
        val aluno = session.getUsuario() ?: return

        if (professorId == 0) {
            Toast.makeText(this, "Professor não definido.", Toast.LENGTH_SHORT).show()
            return
        }

        val texto = edtMensagem.text.toString().trim()
        if (texto.isBlank()) return

        val req = MensagemRequest(
            conteudo_id = conteudoId,
            aluno_id = aluno.id,
            professor_id = professorId,
            origem = "aluno",
            texto = texto
        )

        api.enviarMensagem(req).enqueue(object : Callback<MensagemResponse> {
            override fun onResponse(
                call: Call<MensagemResponse>,
                response: Response<MensagemResponse>
            ) {
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    edtMensagem.setText("")
                    carregarMensagens()
                } else {
                    Toast.makeText(
                        this@ConteudoDetalheActivity,
                        "Erro ao enviar mensagem.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MensagemResponse>, t: Throwable) {
                Toast.makeText(
                    this@ConteudoDetalheActivity,
                    "Falha na conexão.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // ================== FAVORITO ==================

    // Verifica no servidor se já é favorito e ajusta o texto do botão
    private fun verificarStatusFavorito() {
        val aluno = session.getUsuario() ?: return

        api.favoritoStatus(aluno.id, conteudoId)
            .enqueue(object : Callback<FavoritoResponse> {
                override fun onResponse(
                    call: Call<FavoritoResponse>,
                    response: Response<FavoritoResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        if (body.sucesso && body.erro == null) {
                            favoritoAtual = body.favorito
                            btnFavorito.text =
                                if (favoritoAtual) "Remover dos favoritos" else "Favoritar"
                        }
                    }
                    // Se não for sucesso, deixa o default "Favoritar"
                }

                override fun onFailure(call: Call<FavoritoResponse>, t: Throwable) {
                    // Em falha, também mantemos o padrão "Favoritar"
                }
            })
    }

    // Toggle quando o usuário clica
    private fun toggleFavorito() {
        val aluno = session.getUsuario() ?: return

        api.favoritar(
            FavoritoRequest(
                aluno_id = aluno.id,
                conteudo_id = conteudoId
            )
        ).enqueue(object : Callback<FavoritoResponse> {
            override fun onResponse(
                call: Call<FavoritoResponse>,
                response: Response<FavoritoResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.erro != null) {
                        Toast.makeText(
                            this@ConteudoDetalheActivity,
                            body.erro,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        favoritoAtual = body.favorito
                        btnFavorito.text =
                            if (favoritoAtual) "Remover dos favoritos" else "Favoritar"
                    }
                } else {
                    Toast.makeText(
                        this@ConteudoDetalheActivity,
                        "Erro ao favoritar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<FavoritoResponse>, t: Throwable) {
                Toast.makeText(
                    this@ConteudoDetalheActivity,
                    "Falha na conexão.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
