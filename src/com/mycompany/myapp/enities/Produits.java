/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.enities;

/**
 *
 * @author yeekt
 */
public class Produits {
     private int idProd,qteStock;
    private String refProd,designation,image;
    private float prix;

    public Produits() {
    }

    public Produits(int idProd, int qteStock, String refProd, String designation, String image, float prix) {
        this.idProd = idProd;
        this.qteStock = qteStock;
        this.refProd = refProd;
        this.designation = designation;
        this.image = image;
        this.prix = prix;
    }

    public Produits(int qteStock, String refProd, String designation, float prix) {
        this.qteStock = qteStock;
        this.refProd = refProd;
        this.designation = designation;
        this.prix = prix;
    }
    

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public String getRefProd() {
        return refProd;
    }

    public void setRefProd(String refProd) {
        this.refProd = refProd;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
    
    
}
