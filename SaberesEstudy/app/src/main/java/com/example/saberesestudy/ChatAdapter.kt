package com.example.saberesestudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saberesestudy.model.Mensagem

class ChatAdapter(
    private var mensagens: List<Mensagem>
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    fun atualizar(novas: List<Mensagem>) {
        mensagens = novas
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtAutor: TextView = view.findViewById(R.id.txtAutor)
        val txtMensagem: TextView = view.findViewById(R.id.txtMensagem)
        val txtHora: TextView = view.findViewById(R.id.txtHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mensagem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mensagens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = mensagens[position]
        val autor =
            if (m.origem == "aluno") m.aluno_nome ?: "Você" else m.professor_nome ?: "Professor"

        holder.txtAutor.text = autor
        holder.txtMensagem.text = m.texto
        holder.txtHora.text = m.enviado_em
    }
}
