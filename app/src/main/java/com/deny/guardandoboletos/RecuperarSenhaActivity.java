package com.deny.guardandoboletos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private ImageButton buttonRecuperarSenha;
    private EditText    recebeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        buttonRecuperarSenha = findViewById(R.id.imageButtonRecuperarSenha);
        recebeEmail          = findViewById(R.id.editTextRecuperarEmail);


    }
}