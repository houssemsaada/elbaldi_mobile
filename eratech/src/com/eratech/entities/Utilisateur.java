package com.eratech.entities;

public class Utilisateur {

    private int id;
    private String email;
    private String username;
    public Utilisateur() {
    }

    public Utilisateur(int id, String email) {
        this.id = id;
        this.email = email;
    }
    public Utilisateur(int id) {
        this.id = id;
    }

    public Utilisateur(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return username ;
    }

   


}