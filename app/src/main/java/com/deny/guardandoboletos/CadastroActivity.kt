package com.deny.guardandoboletos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.deny.guardandoboletos.config.ConfiguracaoFirebase
import com.deny.guardandoboletos.helper.Base64Custom
import com.deny.guardandoboletos.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {

    var autenticacao: FirebaseAuth = ConfiguracaoFirebase.getAutenticacao()
    lateinit var usuario: Usuario
    lateinit var email: EditText
    lateinit var senha: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        supportActionBar?.setTitle("Tela de cadastro")


        email = findViewById(R.id.editTextCadastroEmail)
        senha = findViewById(R.id.editTextCadastroSenha)

        var buttonCadastrar: ImageButton = findViewById(R.id.imageButtonMeCadastrar)

        buttonCadastrar.setOnClickListener(View.OnClickListener {
            cadastrar()
        })
    }

    fun cadastrar(){
        var recebeEmail: String = email.text.toString()
        var recebeSenha: String = senha.text.toString()

        if (!recebeEmail.isEmpty()){
            if (!recebeSenha.isEmpty()){
                usuario = Usuario()
                usuario.setEmail(recebeEmail)
                usuario.setSenha(recebeSenha)

                finalizarCadastro(usuario.getEmail(), usuario.getSenha())
            } else {
                Toast.makeText(this, "Digite alguma senha", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Digite algum email", Toast.LENGTH_SHORT).show()
        }
    }

    fun finalizarCadastro(email: String, senha: String){
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful){

                    var idUsuario: String = Base64Custom.codificarBase64( usuario.getEmail() )
                    usuario.setIdUsuario(idUsuario)
                    usuario.salvar()

                    Toast.makeText(
                        this,
                        "Sucesso ao cadastrar",
                        Toast.LENGTH_SHORT
                    ).show()

                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    finish()

                } else {
                    var excecao = ""
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        excecao = "Digite uma senha mais forte"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        excecao = "Digite um e-mail válido"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        excecao = "Este usuario já existe"
                    } catch (e: Exception) {
                        excecao = "Erro ao cadastrar usuario" + e.message
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