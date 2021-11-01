package com.deny.guardandoboletos.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deny.guardandoboletos.LoginActivity
import com.deny.guardandoboletos.R
import com.deny.guardandoboletos.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageButtonMudarConta.setOnClickListener(View.OnClickListener {
            var intentLogin: Intent = Intent(root.context, LoginActivity::class.java)
            startActivity(intentLogin)
            firebaseAuth.signOut()
            activity?.finish()
        })

        binding.imageButtonSair.setOnClickListener(View.OnClickListener {
            firebaseAuth.signOut()
            activity?.finish()
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}