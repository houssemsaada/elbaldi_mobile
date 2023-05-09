package com.eratech.entities;

import java.util.Date;

public class Commentaire {

    private int id;
    private String contenu;
    private Date date;
    private Utilisateur utilisateur;
    private Produit produit;

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, Date date, Utilisateur utilisateur, Produit produit) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.utilisateur = utilisateur;
        this.produit = produit;
    }

    public Commentaire(String contenu, Date date, Utilisateur utilisateur, Produit produit) {
        this.contenu = contenu;
        this.date = date;
        this.utilisateur = utilisateur;
        this.produit = produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }


    @Override
    public String toString() {
        return "Commentaire : " +
                "id=" + id
                + "\\n Contenu=" + contenu
                + "\\n Date=" + date
                + "\\n Utilisateur=" + utilisateur
                + "\\n Produit=" + produit
                ;
    }


}