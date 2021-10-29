package com.deny.guardandoboletos.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.adapter.BoletoAdapter
import com.deny.guardandoboletos.databinding.FragmentHomeBinding
import com.deny.guardandoboletos.helper.Base64Custom
import com.deny.guardandoboletos.helper.RecyclerItemClickListener
import com.deny.guardandoboletos.model.Boleto
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        coroutine()
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


        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                boletosAdapter.removeAt(viewHolder.adapterPosition)
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_conteudoActivity)
            }
            //...
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewConsultar)

        var recyclerView: RecyclerView = binding.recyclerViewConsultar

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context, recyclerView, object :
                RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    var resultId = listBoletos[position].id
                    var resultTitulo = listBoletos[position].titulo
                    var resultData = listBoletos[position].dataBoleto
                    var resultPrioridade = listBoletos[position].prioridadeBoleto
                    var resultValor = listBoletos[position].valorBoleto
                    var resultAvatar = listBoletos[position].avatar
                    var bundle: Bundle = Bundle()

                    setFragmentResult("requestKeyId", bundleOf("bundleKeyId" to resultId))
                    setFragmentResult("requestKeyTitulo", bundleOf("bundleKeyTitulo" to resultTitulo))
                    setFragmentResult("requestKeyData", bundleOf("bundleKeyData" to resultData))
                    setFragmentResult("requestKeyPrioridade", bundleOf("bundleKeyPrioridade" to resultPrioridade))
                    setFragmentResult("requestKeyValor", bundleOf("bundleKeyValor" to resultValor))
                    setFragmentResult("requestKeyAvatar", bundleOf("bundleKeyAvatar" to resultAvatar))

                    listBoletos.set(position, boleto)
                    boletosAdapter.notifyItemChanged(position)

                    Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_editarBoletoFragment)

                }

                override fun onLongItemClick(view: View, position: Int) {
                    Toast.makeText(root.context,
                        "Caso queira excluir o item desejado arraste para alguns dos lados",
                        Toast.LENGTH_LONG).show()
                }
            })
        )

        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_adicionarBoletoFragment)

            Snackbar.make(view, "Você irá adicionar o boleto", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        return root
    }

    fun coroutine() = runBlocking {
        launch {
            delay(1000L)
            consultar()
        }
    }

    fun consultar(){

        var autenticacao: FirebaseAuth = FirebaseAuth.getInstance()
        var id: String = Base64Custom.codificarBase64(autenticacao.currentUser?.getEmail())

            firestoreDB.collection("boletos").get().addOnCompleteListener(OnCompleteListener {
                for (dataObject in it.getResult()!!.documents){
                    var note = dataObject.toObject(Boleto::class.java)

                    if (note!!.id.equals(id)) {
                        note!!.id = dataObject.id

                        //var p: Boleto = Boleto(nome = note!!.nome, anoEscolar = note!!.anoEscolar, avatar = note!!.avatar)
                        var p: Boleto = Boleto(
                            titulo           = note!!.titulo,
                            avatar           = note!!.avatar,
                            prioridadeBoleto = note!!.prioridadeBoleto,
                            valorBoleto      = note!!.valorBoleto,
                            dataBoleto       = note!!.dataBoleto
                        )
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