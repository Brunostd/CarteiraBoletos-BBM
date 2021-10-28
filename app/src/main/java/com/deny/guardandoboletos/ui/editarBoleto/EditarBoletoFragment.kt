package com.deny.guardandoboletos.ui.editarBoleto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.EditarBoletoFragmentBinding
import com.deny.guardandoboletos.helper.DateUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class EditarBoletoFragment : Fragment() {

    private lateinit var editarBoletoViewModel: EditarBoletoViewModel
    private var _binding: EditarBoletoFragmentBinding? = null

    var firestoreDB : FirebaseFirestore = FirebaseFirestore.getInstance()

    var         recebeId: String = ""
    var     recebeAvatar: Int = R.drawable.conta_raio
    var     recebeTitulo: String = ""
    var       recebeData: String = ""
    var recebePrioridade: String = ""
    var      recebeValor: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editarBoletoViewModel =
            ViewModelProvider(this).get(EditarBoletoViewModel::class.java)

        (context as AppCompatActivity).supportActionBar!!.title = "Editar boleto"

        _binding = EditarBoletoFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recuperarEdicao()
        buttonPrioridade(root)

        binding.imageButtonEditarCadastrarBoleto.setOnClickListener(View.OnClickListener {
            
            var userDetail: MutableMap<String, Any> = HashMap()
            userDetail.put("titulo", binding.editTextEditarTituloBoleto.text.toString())

            firestoreDB.collection("boletos")
                .whereEqualTo("titulo", recebeTitulo).get()
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful && !it.getResult()?.isEmpty!!) {

                        println("NOME: " + recebeTitulo)
                        var documentSnapShot: DocumentChange? =
                            it.getResult()!!.getDocumentChanges().get(0)
                        var documentID: String = documentSnapShot!!.document.id
                        firestoreDB.collection("boletos")
                            .document(documentID)
                            .update(userDetail)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    root.context,
                                    "Titulo atualizado com sucesso",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            .addOnFailureListener(OnFailureListener {
                                Toast.makeText(
                                    root.context,
                                    "Falha ao atualizar o titulo",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                    } else {
                        Toast.makeText(root.context, "Erro titulo", Toast.LENGTH_LONG).show()
                    }
                })

            //Alterar o ano escolar do aluno
            var userDetail2: MutableMap<String, Any> = HashMap()
            userDetail2.put("dataBoleto", binding.editTextEditarDataBoleto.text.toString())

            firestoreDB.collection("boletos")
                .whereEqualTo("titulo", recebeTitulo).get()
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful && !it.getResult()?.isEmpty!!) {

                        println("Data: " + recebeData)
                        var documentSnapShot: DocumentChange? =
                            it.getResult()!!.getDocumentChanges().get(0)
                        var documentID: String = documentSnapShot!!.document.id
                        firestoreDB.collection("boletos")
                            .document(documentID)
                            .update(userDetail2)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    root.context,
                                    "Data atualizada com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener(OnFailureListener {
                                Toast.makeText(
                                    root.context,
                                    "Falha ao atualizar a data",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    } else {
                        Toast.makeText(root.context, "Erro data", Toast.LENGTH_LONG)
                            .show()
                    }
                })

            var userDetail3: MutableMap<String, Any> = HashMap()
            userDetail3.put("valorBoleto", binding.editTextEditarValorBoleto.text.toString())

            firestoreDB.collection("boletos")
                .whereEqualTo("titulo", recebeTitulo).get()
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful && !it.getResult()?.isEmpty!!) {

                        println("Valor: " + recebeValor)
                        var documentSnapShot: DocumentChange? =
                            it.getResult()!!.getDocumentChanges().get(0)
                        var documentID: String = documentSnapShot!!.document.id
                        firestoreDB.collection("boletos")
                            .document(documentID)
                            .update(userDetail3)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    root.context,
                                    "Valor atualizado com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener(OnFailureListener {
                                Toast.makeText(
                                    root.context,
                                    "Falha ao atualizar o valor",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    } else {
                        Toast.makeText(root.context, "Erro valor", Toast.LENGTH_LONG)
                            .show()
                    }
                })

            var userDetail4: MutableMap<String, Any> = HashMap()
            userDetail4.put("prioridadeBoleto", recebePrioridade)

            firestoreDB.collection("boletos")
                .whereEqualTo("titulo", recebeTitulo).get()
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful && !it.getResult()?.isEmpty!!) {

                        println("Prioridade: " + recebePrioridade)
                        var documentSnapShot: DocumentChange? =
                            it.getResult()!!.getDocumentChanges().get(0)
                        var documentID: String = documentSnapShot!!.document.id
                        firestoreDB.collection("boletos")
                            .document(documentID)
                            .update(userDetail4)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    root.context,
                                    "Prioridade atualizada com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener(OnFailureListener {
                                Toast.makeText(
                                    root.context,
                                    "Falha ao atualizar a prioridade",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    } else {
                        Toast.makeText(root.context, "Erro prioridade", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            Navigation.findNavController(root).navigate(R.id.action_editarBoletoFragment_to_navigation_home)
        })

        return root
    }

    fun buttonPrioridade(
        root: View = binding.root
    ){
        binding.buttonEditarPrioridadeBaixa.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade baixa"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
            binding.textViewEditarPrioridade.setText(recebePrioridade)
        })

        binding.buttonEditarPrioridadeMedia.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade media"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
            binding.textViewEditarPrioridade.setText(recebePrioridade)
        })

        binding.buttonEditarPrioridadeAlta.setOnClickListener(View.OnClickListener {
            recebePrioridade = "Prioridade alta"
            Toast.makeText(root.context,
                "Você escolheu: " + recebePrioridade,
                Toast.LENGTH_SHORT).show()
            binding.textViewEditarPrioridade.setText(recebePrioridade)
        })
    }

    fun recuperarEdicao(){
        setFragmentResultListener("requestKeyId"){requestKey, bundle ->
            val resultId = bundle.getString("bundleKeyId")
                recebeId = resultId.toString()
        }

        setFragmentResultListener("requestKeyAvatar"){requestKey, bundle ->
            val resultImagem = bundle.getInt("bundleKeyAvatar")
                recebeAvatar = resultImagem
            Glide.with(this).load(recebeAvatar).into(binding.imageViewEditarTipoBoleto)
        }

        setFragmentResultListener("requestKeyTitulo"){requestKey, bundle ->
            val resultTitulo = bundle.getString("bundleKeyTitulo")
                recebeTitulo = resultTitulo.toString()
            binding.editTextEditarTituloBoleto.setText(recebeTitulo)
        }

        setFragmentResultListener("requestKeyData"){requestKey, bundle ->
            val resultData = bundle.getString("bundleKeyData")
                recebeData = resultData.toString()
            binding.editTextEditarDataBoleto.setText(recebeData)
        }

        setFragmentResultListener("requestKeyValor"){requestKey, bundle ->
            val resultValor = bundle.getString("bundleKeyValor")
                recebeValor = resultValor.toString()
            binding.editTextEditarValorBoleto.setText(recebeValor)
        }

        setFragmentResultListener("requestKeyPrioridade"){requestKey, bundle ->
            val resultPrioridade = bundle.getString("bundleKeyPrioridade")
                recebePrioridade = resultPrioridade.toString()
            binding.textViewEditarPrioridade.setText(recebePrioridade)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}