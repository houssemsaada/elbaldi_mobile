/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eratech.entities;

import com.eratech.entities.Produit;
import com.eratech.entities.Utilisateur;
import java.util.List;

/**
 *
 * @author houss
 */

public class panier {

    private int id_panier, nombre_article;
    List<Produit> list;
    private Utilisateur u1;
    private float total_panier;
    //constructeur par defaut

    public panier() {
    }
    ///constructeur parametre 

    public panier(int id_panier) {
        this.id_panier = id_panier;
    }
    

    public panier(int id_panier, Utilisateur u1) {
        this.id_panier = id_panier;
        this.u1 = u1;
    }

    public panier(Utilisateur u1, int nombre_article, float total_panier) {
        this.u1 = u1;
        this.nombre_article = nombre_article;
        this.total_panier = total_panier;
    }

    public panier(List<Produit> list, Utilisateur u1) {
        this.list = list;
        this.u1 = u1;
    }

    public panier(int id_panier, List<Produit> list, Utilisateur u1) {
        this.id_panier = id_panier;
        this.list = list;
        this.u1 = u1;
    }

    public panier(Utilisateur u1) {
        this.u1 = u1;
    }

    public panier(Produit p1, Utilisateur u1, int nombre_article, float total_panier) {
        this.u1 = u1;
        this.nombre_article = nombre_article;
        this.total_panier = total_panier;
    }



    //getters
    public int getId_panier() {
        return id_panier;
    }

    public int getNombre_article() {
        return nombre_article;
    }

    public List<Produit> getList() {
        return list;
    }


    public float getTotal_panier() {
        return total_panier;
    }

    public Utilisateur getU1() {
        return u1;
    }

    //setters
    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public void setList(List<Produit> list) {
        this.list = list;
    }


    public void setNombre_article(int nombre_article) {
        this.nombre_article = nombre_article;
    }

    public void setTotal_panier(float total_panier) {
        this.total_panier = total_panier;
    }

    public void setU1(Utilisateur u1) {
        this.u1 = u1;
    }

    @Override
    public String toString() {
        return "panier{" +"\n" + "id_panier=" + id_panier +"\n"+ " nombre_article=" + nombre_article +"\n"+ ", produits=" + list +"\n"+ ", utilisateur=" + u1 +"\n"+ ", total_panier=" + total_panier + '}'+"\n";
    }

    public int numArticle(List<Produit> list) {
        int size = list.size();
        return size;
    }
}