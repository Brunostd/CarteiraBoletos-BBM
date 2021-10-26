package com.deny.guardandoboletos.ui.adicionarBoleto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.AdicionarBoletoFragmentBinding
import com.deny.guardandoboletos.databinding.FragmentHomeBinding
import com.deny.guardandoboletos.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class AdicionarBoletoFragment : Fragment() {

    private lateinit var adicionarBoletoViewModel: AdicionarBoletoViewModel
    private var _binding: AdicionarBoletoFragmentBinding? = null

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}