package com.example.saberesestudy.util

import android.content.Context
import com.example.saberesestudy.model.Usuario

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    fun salvarUsuario(usuario: Usuario) {
        prefs.edit()
            .putInt("USER_ID", usuario.id)
            .putString("USER_NOME", usuario.nome)
            .putString("USER_EMAIL", usuario.email)
            .putString("USER_TIPO", usuario.tipo)   // "aluno" ou "professor"
            .putString("USER_SERIE", usuario.serie) // <- série salva aqui
            .apply()
    }

    fun getUsuario(): Usuario? {
        val id = prefs.getInt("USER_ID", 0)
        if (id == 0) return null

        return Usuario(
            id = id,
            nome = prefs.getString("USER_NOME", "") ?: "",
            email = prefs.getString("USER_EMAIL", "") ?: "",
            tipo = prefs.getString("USER_TIPO", "aluno"),
            serie = prefs.getString("USER_SERIE", null) // <- lida aqui
        )
    }

    fun limpar() {
        prefs.edit().clear().apply()
    }
}
