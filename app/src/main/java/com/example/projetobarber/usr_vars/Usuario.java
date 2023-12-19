package com.example.projetobarber.usr_vars;

public class Usuario implements java.io.Serializable{
    private String nome, senha;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario() {

    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}
