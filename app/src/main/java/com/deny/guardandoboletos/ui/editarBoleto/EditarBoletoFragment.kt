package com.deny.guardandoboletos.ui.editarBoleto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.EditarBoletoFragmentBinding
import com.deny.guardandoboletos.databinding.FragmentDashboardBinding
import com.deny.guardandoboletos.ui.dashboard.DashboardViewModel

class EditarBoletoFragment : Fragment() {

    private lateinit var editarBoletoViewModel: EditarBoletoViewModel
    private var _binding: EditarBoletoFragmentBinding? = null

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

        _binding = EditarBoletoFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}