package com.eratech.entities;

public class Produit {

    private String ref;
    private String libelle;
    private String description;
    private String image;
    private float prixVente;
    private Categorie categorie;

    public Produit() {
    }

    public Produit(String ref, String libelle, String description, String image, float prixVente, Categorie categorie) {
        this.ref = ref;
        this.libelle = libelle;
        this.description = description;
        this.image = image;
        this.prixVente = prixVente;
        this.categorie = categorie;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return  libelle ;
    }


}