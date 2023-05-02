/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author houss
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mEtrOpOliS
 */


public class Utilisateur {
    private int id_user;
    private String nom;
    private String prenom;
    private String email;



    private Date dateNaissance;
    private int numTel;

    private String ville;
    private String mdp;



    public Utilisateur() {
    }

    public Utilisateur(String email) {
        this.email = email;
    }




  
    public Utilisateur(int id_user) {
        this.id_user = id_user;
    }
    


 



    public Utilisateur(String nom, String prenom, String email, Date dateNaissance, int numTel, String ville, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.ville = ville;
        this.mdp = mdp;
    }

    public Utilisateur(int id_user, String nom, String prenom, String email, Date dateNaissance, int numTel, String ville, String mdp) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.ville = ville;
        this.mdp = mdp;
    }

    public Utilisateur(int id_user, String nom, String prenom, String email, Date dateNaissance, int numTel, String ville) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.ville = ville;
    }


 
    


    public int getid_user() {
        return id_user;
    }

    public void setid_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }



    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }



    @Override
    public String toString() {

        return  nom ;

    }

 
 
    
}

