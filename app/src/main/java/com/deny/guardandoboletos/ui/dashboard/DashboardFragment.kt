package com.deny.guardandoboletos.ui.dashboard

import android.os.Bundle
import android.sax.Element
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.FragmentDashboardBinding
import mehdi.sakout.aboutpage.AboutPage

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var descricao: String = "A Carteira de boletos foi uma ideia pessoal " +
                "para demonstrar um pouco do conhecimento que possuo " +
                "e para de certa forma ajudar você a organizar os seus boletos e os meus."

        var versao: mehdi.sakout.aboutpage.Element = mehdi.sakout.aboutpage.Element()
        versao.setTitle("Version 1.0")

        return AboutPage(activity)
            .setImage(R.drawable.logotipo)
            .setDescription(descricao)
            .addGroup("Entre em contato")
            .addEmail("batioe.std@gmail.com", "Envie um e-mail")
            .addWebsite("https://www.linkedin.com/in/bruno-brito-53b621182/", "Acesse meu linkedin")
            .addGroup("Redes Sociais")
            .addFacebook("brunostd/", "Facebook")
            .addInstagram("bruno.std/", "Instagram")
            .addGitHub("Brunostd/", "GitHub")
            .addGroup("Referencias")
            .addWebsite("https://www.flaticon.com/br/", "Alguns icones retirados da FlatIcon")
            .addItem(versao)
            .create()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}