package com.deny.guardandoboletos.ui.escolherImagem

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.adapter.ImagemAdapter
import com.deny.guardandoboletos.databinding.AdicionarBoletoFragmentBinding
import com.deny.guardandoboletos.databinding.EscolherImagemFragmentBinding
import com.deny.guardandoboletos.helper.RecyclerItemClickListener
import com.deny.guardandoboletos.model.Imagem
import com.deny.guardandoboletos.ui.adicionarBoleto.AdicionarBoletoViewModel

class EscolherImagemFragment : Fragment() {

    private lateinit var escolherImagemViewModel: EscolherImagemViewModel
    private var _binding: EscolherImagemFragmentBinding? = null
    private var listaImagem: MutableList<Imagem> = ArrayList<Imagem>()
    lateinit var imagem: Imagem

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        consultarImagem()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        escolherImagemViewModel =
            ViewModelProvider(this).get(EscolherImagemViewModel::class.java)

        _binding = EscolherImagemFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (context as AppCompatActivity).supportActionBar!!.title = "Escolher tipo de boleto"


        binding.recyclerViewImagem.adapter = ImagemAdapter(listaImagem)
        binding.recyclerViewImagem.layoutManager = GridLayoutManager(context, 2)

        var recyclerView: RecyclerView = binding.recyclerViewImagem

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context, recyclerView, object :
                RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {

                    val resultImagem = listaImagem[position].escolherImagem
                    var bundle: Bundle = Bundle()
                    // Use the Kotlin extension in the fragment-ktx artifact
                    setFragmentResult("requestKeyImagem", bundleOf("bundleKeyImagem" to resultImagem))

                    Navigation.findNavController(root).navigate(R.id.action_escolherImagemFragment_to_adicionarBoletoFragment)

                }

                override fun onLongItemClick(view: View, position: Int) {

                }
            })
        )

        return root
    }

    fun consultarImagem(){
        imagem = Imagem(R.drawable.conta_agua)
        listaImagem.add(imagem)

        imagem = Imagem(R.drawable.conta_raio)
        listaImagem.add(imagem)

        imagem = Imagem(R.drawable.conta_credito)
        listaImagem.add(imagem)

        imagem = Imagem(R.drawable.conta_deposito)
        listaImagem.add(imagem)

        imagem = Imagem(R.drawable.outros)
        listaImagem.add(imagem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}