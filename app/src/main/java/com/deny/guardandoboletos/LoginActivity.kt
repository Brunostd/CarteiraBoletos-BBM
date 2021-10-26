package com.deny.guardandoboletos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.deny.guardandoboletos.config.ConfiguracaoFirebase
import com.deny.guardandoboletos.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    var          autenticacao: FirebaseAuth = ConfiguracaoFirebase.getAutenticacao()
    lateinit var      usuario: Usuario
    lateinit var        email: EditText
    lateinit var        senha: EditText
    lateinit var buttonEntrar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        email        = findViewById(R.id.editTextLoginEmail)
        senha        = findViewById(R.id.editTextLoginSenha)
        buttonEntrar = findViewById(R.id.imageButtonLoginEntrar)

        val imageButtonCadastrar: ImageButton = findViewById(R.id.imageButtonLoginCadastrar)
        val intent = Intent(this, CadastroActivity::class.java)

        buttonEntrar.setOnClickListener(View.OnClickListener {
            login()
        })

        imageButtonCadastrar.setOnClickListener(View.OnClickListener {
            startActivity(intent)
            finish()
        })
    }

    fun login(){

        var recebeEmail: String = email.text.toString()
        var recebeSenha: String = senha.text.toString()

        if (!recebeEmail.isEmpty()){
            if (!recebeSenha.isEmpty()){
                usuario = Usuario()
                usuario.setEmail(recebeEmail)
                usuario.setSenha(recebeSenha)

                finalizandoLogin(usuario.email, usuario.senha)

            } else{
                Toast.makeText(
                    this,
                    "Digite alguma senha",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else{
            Toast.makeText(
                this,
                "Digite algum email",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun finalizandoLogin(email: String, senha: String){
        autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful){
                    var intent2 = Intent(this, ConteudoActivity::class.java)
                    startActivity(intent2)

                    finish()
                } else {
                    var excecao = ""
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        excecao = "E-mail ou senha não corresponde a um usuario cadastrado"
                    } catch (e: FirebaseAuthInvalidUserException) {
                        excecao = "Usuario não está cadastrado"
                    } catch (e: Exception) {
                        excecao = "Erro ao fazer login" + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(
                        this,
                        excecao,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}