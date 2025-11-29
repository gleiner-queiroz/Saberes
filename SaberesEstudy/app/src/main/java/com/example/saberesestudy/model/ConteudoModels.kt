package com.example.saberesestudy.model

data class Conteudo(
    val id: Int,
    val titulo: String,
    val materia: String,
    val serie: String,
    val professor_nome: String? = null,
    val criado_em: String? = null
)

data class ConteudoDetalhe(
    val id: Int,
    val titulo: String,
    val materia: String,
    val serie: String,
    val texto: String?,
    val imagens: String?,
    val videos: String?,
    val links: String?,
    val professor_id: Int,
    val professor_nome: String
)