package com.deny.guardandoboletos.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.adapter.BoletoAdapter
import com.deny.guardandoboletos.databinding.FragmentHomeBinding
import com.deny.guardandoboletos.helper.Base64Custom
import com.deny.guardandoboletos.model.Boleto
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    var firestoreDB : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var listBoletos: MutableList<Boleto> = ArrayList<Boleto>()
    var boleto: Boleto = Boleto()
    var boletosAdapter: BoletoAdapter = BoletoAdapter(listBoletos)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        consultar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_adicionarBoletoFragment)

            Snackbar.make(view, "Você irá adicionar o boleto", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        return root
    }

    fun consultar(){

        var autenticacao: FirebaseAuth = FirebaseAuth.getInstance()
        var id: String = Base64Custom.codificarBase64(autenticacao.currentUser?.getEmail())
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

        firestoreDB.collection("boletos").get().addOnCompleteListener(OnCompleteListener {
            for (dataObject in it.getResult()!!.documents){
                var note = dataObject.toObject(Boleto::class.java)

                if (note!!.id.equals(id)) {
                    note!!.id = dataObject.id

                    //var p: Boleto = Boleto(nome = note!!.nome, anoEscolar = note!!.anoEscolar, avatar = note!!.avatar)
                    var p: Boleto = Boleto(titulo = note!!.titulo, avatar = note!!.avatar, prioridadeBoleto = note!!.prioridadeBoleto)
                    this.listBoletos.add(p)

                    binding.recyclerViewConsultar.adapter = BoletoAdapter(listBoletos)
                    binding.recyclerViewConsultar.layoutManager = GridLayoutManager(context, 2)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}