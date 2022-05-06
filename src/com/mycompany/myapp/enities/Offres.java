/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.enities;

/**
 *
 * @author user
 */
public class Offres {
    private int id_offre,id_user;
    private String nom,description,ville,type,categ,image;
    private float prix;

    public Offres() {
    }

    public Offres(int id_user, String nom, String description, String ville, String type, String categ, float prixn,String img) {
        this.id_user = id_user;
        this.nom = nom;
        this.description = description;
        this.ville = ville;
        this.type = type;
        this.categ = categ;
        this.prix = prix;
        this.image=img;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Offres{" + "id_offre=" + id_offre + ", id_user=" + id_user + ", nom=" + nom + ", description=" + description + ", ville=" + ville + ", type=" + type + ", categ=" + categ + ", image=" + image + ", prix=" + prix + '}';
    }

    
}
