package com.deny.guardandoboletos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageButton buttonRecuperarSenha;
    private EditText    recebeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        buttonRecuperarSenha = findViewById(R.id.imageButtonRecuperarSenha);
        recebeEmail          = findViewById(R.id.editTextRecuperarEmail);
        firebaseAuth         = FirebaseAuth.getInstance();

        buttonRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth
                        .sendPasswordResetEmail(recebeEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(
                                            RecuperarSenhaActivity.this,
                                            "Recuperação de acesso iniciada. Email enviado",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    startActivity(new Intent(RecuperarSenhaActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(
                                            RecuperarSenhaActivity.this,
                                            "Falhou! Tente novamente",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        });
            }
        });

    }
}