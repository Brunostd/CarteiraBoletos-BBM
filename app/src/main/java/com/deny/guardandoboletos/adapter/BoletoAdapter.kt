package com.deny.guardandoboletos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.model.Boleto

class BoletoAdapter(var listaBoletos: MutableList<Boleto>):
    RecyclerView.Adapter<BoletoAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(boleto: Boleto){
            var         id:     String    = boleto.id
            var     avatar:     ImageView = itemView.findViewById(R.id.imageViewBoleto)
            var prioridade:     TextView  = itemView.findViewById(R.id.textViewPrioridadeBoleto)
            var     titulo:     TextView  = itemView.findViewById(R.id.textViewTituloBoleto)
            var       data:     String    = boleto.dataBoleto

            avatar.setImageResource(boleto.avatar)
            titulo.setText(boleto.titulo)
            prioridade.setText(boleto.prioridadeBoleto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.boleto, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaBoletos[position])
    }

    override fun getItemCount(): Int = listaBoletos.size
}