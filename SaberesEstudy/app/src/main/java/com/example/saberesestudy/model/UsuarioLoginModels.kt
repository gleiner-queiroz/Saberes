package com.example.saberesestudy.model

data class Usuario(
    val id: Int,
    val nome: String,
    val email: String,
    val tipo: String? = "aluno",
    val serie: String? = null
)

data class LoginRequest(
    val email: String,
    val senha: String,
    val tipo: String = "aluno"
)

data class LoginResponse(
    val sucesso: Boolean,
    val usuario: Usuario?,
    val erro: String?
)
