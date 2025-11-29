package com.example.saberesestudy.model

data class FavoritoRequest(
    val aluno_id: Int,
    val conteudo_id: Int
)

data class FavoritoResponse(
    val sucesso: Boolean,
    val favorito: Boolean,
    val erro: String?
)
