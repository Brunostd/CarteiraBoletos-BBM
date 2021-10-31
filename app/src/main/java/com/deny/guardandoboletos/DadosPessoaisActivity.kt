package com.deny.guardandoboletos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class DadosPessoaisActivity : AppCompatActivity() {

    lateinit var imageButtonAceito: ImageButton
    lateinit var imageButtonNaoAceito: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_pessoais)

        supportActionBar?.hide()

        imageButtonAceito    = findViewById(R.id.imageButtonAceitarContinuar)
        imageButtonNaoAceito = findViewById(R.id.imageButtonNaoAceitar)

        var intentAceito: Intent = Intent(this, CadastroActivity::class.java)

        imageButtonAceito.setOnClickListener(View.OnClickListener {
            startActivity(intentAceito)
            finish()
        })

        imageButtonNaoAceito.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}