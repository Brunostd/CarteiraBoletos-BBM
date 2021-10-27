package com.deny.guardandoboletos.ui.adicionarBoleto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.AdicionarBoletoFragmentBinding
import com.deny.guardandoboletos.databinding.FragmentHomeBinding
import com.deny.guardandoboletos.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class AdicionarBoletoFragment : Fragment() {

    private lateinit var adicionarBoletoViewModel: AdicionarBoletoViewModel
    private var _binding: AdicionarBoletoFragmentBinding? = null
    var recebeImagem: Int = R.drawable.conta_agua

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adicionarBoletoViewModel =
            ViewModelProvider(this).get(AdicionarBoletoViewModel::class.java)

        _binding = AdicionarBoletoFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (context as AppCompatActivity).supportActionBar!!.title = "Adicionar boleto"

        recuperarEdicao()

        var recebeTituloBoleto: String = binding.editTextTituloBoleto.text.toString()
        var recebeDataBoleto: String = binding.editTextDataBoleto.text.toString()

        binding.imageViewTipoBoleto.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_adicionarBoletoFragment_to_escolherImagemFragment)
        })

        binding.imageButtonTipoBoleto.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_adicionarBoletoFragment_to_escolherImagemFragment)
        })

        return root
    }

    fun recuperarEdicao(){
        setFragmentResultListener("requestKeyImagem"){requestKey, bundle ->
            val resultImagem = bundle.getInt("bundleKeyImagem")
            recebeImagem = resultImagem
            Glide.with(this).load(recebeImagem).into(binding.imageViewTipoBoleto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}