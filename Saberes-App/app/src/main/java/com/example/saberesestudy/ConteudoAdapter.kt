package com.example.saberesestudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saberesestudy.model.Conteudo

class ConteudoAdapter(
    private val listaConteudos: List<Conteudo>,
    private val onItemClick: (Conteudo) -> Unit
) : RecyclerView.Adapter<ConteudoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtMateria: TextView = itemView.findViewById(R.id.txtMateria)
        val txtSerie: TextView = itemView.findViewById(R.id.txtSerie)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(listaConteudos[pos])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conteudo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conteudo = listaConteudos[position]
        holder.txtTitulo.text = conteudo.titulo
        holder.txtMateria.text = "Matéria: ${conteudo.materia}"
        holder.txtSerie.text = "Série: ${conteudo.serie}"
    }

    override fun getItemCount(): Int = listaConteudos.size
}
