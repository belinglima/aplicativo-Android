package com.google.android.gms.location.sample.locationupdatesforegroundservice;

public class Motorista {
    private int id;
    private String nome;
    private String token;
    private String login;
    private String senha;

    public Motorista() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
