package com.deny.guardandoboletos.ui.adicionarBoleto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.config.ConfiguracaoFirebase
import com.deny.guardandoboletos.databinding.AdicionarBoletoFragmentBinding
import com.deny.guardandoboletos.databinding.FragmentHomeBinding
import com.deny.guardandoboletos.helper.Base64Custom
import com.deny.guardandoboletos.helper.DateUtil
import com.deny.guardandoboletos.model.Boleto
import com.deny.guardandoboletos.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarBoletoFragment : Fragment() {

    private lateinit var adicionarBoletoViewModel: AdicionarBoletoViewModel
    private var _binding: AdicionarBoletoFragmentBinding? = null
    var autenticacao: FirebaseAuth = ConfiguracaoFirebase.getAutenticacao()
    var firestoreDB : FirebaseFirestore = FirebaseFirestore.getInstance()
    var recebePrioridade: String = ""
    var     recebeImagem: Int = R.drawable.conta_agua

    var   recebeTituloBoleto: String = ""
    var     recebeDataBoleto: String = ""
    var    recebeValorBoleto: String = ""
    lateinit var  recebeData: EditText

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

        buttonPrioridade(root)
        recuperarEdicao()

        var       id: String = Base64Custom.codificarBase64(autenticacao.currentUser?.getEmail())
          recebeTituloBoleto = binding.editTextTituloBoleto.getText().toString()
           recebeDataBoleto  = binding.editTextDataBoleto.getText().toString()
         recebeValorBoleto   = binding.editTextDataBoleto.getText().toString()
         recebeData          = binding.editTextDataBoleto

        recebeData.setText(DateUtil.dataAtual())

        binding.imageButtonCadastrarBoleto.setOnClickListener(View.OnClickListener {
            if (!binding.editTextTituloBoleto.text.isEmpty() && !binding.editTextValorBoleto.text.isEmpty() && !recebePrioridade.isEmpty() && !binding.editTextDataBoleto.text.isEmpty()){
                var note: Boleto = Boleto(
                    id = id,
                    titulo = binding.editTextTituloBoleto.text.toString(),
                    prioridadeBoleto = recebePrioridade,
                    valorBoleto = binding.editTextValorBoleto.text.toString(),
                    dataBoleto = binding.editTextDataBoleto.text.toString(),
                    avatar = recebeImagem
                )
                firestoreDB.collection("boletos").add(note.toMap())
                Toast.makeText(root.context, "Boletos cadastrado com sucesso", Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(root.context,
                    "Você deixou de digitar o titulo ou a prioridade ou a data ou o valor",
                    Toast.LENGTH_LONG).show()
            }
        })

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

    fun buttonPrioridade(
        root: View = binding.root
    ){
        binding.buttonPrioridadeBaixa.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade baixa"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
        })

        binding.buttonPrioridadeMedia.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade media"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
        })

        binding.buttonPrioridadeAlta.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade alta"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}