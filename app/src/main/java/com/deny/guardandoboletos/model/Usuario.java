package com.deny.guardandoboletos.model;

import com.deny.guardandoboletos.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String idUsuario;
    private String email;
    private String senha;

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabase();
        databaseReference.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }

    public Usuario() {
    }

    public Usuario(String idUsuario, String email, String senha) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senha = senha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
