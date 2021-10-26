package com.deny.guardandoboletos.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;
    private static DatabaseReference database;

    public static FirebaseAuth getAutenticacao(){
        if ( autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    public static DatabaseReference getDatabase(){
        if ( database == null){
            database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }

}
