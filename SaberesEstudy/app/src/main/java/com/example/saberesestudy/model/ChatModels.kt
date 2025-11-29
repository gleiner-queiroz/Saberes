package com.example.saberesestudy.model

data class Mensagem(
    val id: Int,
    val conteudo_id: Int,
    val aluno_id: Int,
    val professor_id: Int,
    val origem: String,          // "aluno" ou "professor"
    val texto: String,
    val enviado_em: String,
    val aluno_nome: String?,
    val professor_nome: String?
)

data class MensagemRequest(
    val conteudo_id: Int,
    val aluno_id: Int,
    val professor_id: Int,
    val origem: String = "aluno",
    val texto: String
)

data class MensagemResponse(
    val sucesso: Boolean,
    val erro: String? = null
)
