package com.deny.guardandoboletos.adapter

import android.content.Context
import android.os.Build.VERSION_CODES.O
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.model.Boleto
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore



class BoletoAdapter(var listaBoletos: MutableList<Boleto>):
    RecyclerView.Adapter<BoletoAdapter.MyViewHolder>() {

    var firestoreDB : FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var view: View

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

    fun removeAt(position: Int) {
        firestoreDB.collection("boletos")
            .whereEqualTo("titulo", listaBoletos[position].titulo).get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful && !it.getResult()?.isEmpty!!) {

                    var documentSnapShot: DocumentChange? =
                        it.getResult()!!.getDocumentChanges().get(0)
                    var documentID: String = documentSnapShot!!.document.id
                    firestoreDB.collection("boletos")
                        .document(documentID)
                        .delete()
                }
            })

        listaBoletos.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, listaBoletos.size)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listaBoletos.size
}