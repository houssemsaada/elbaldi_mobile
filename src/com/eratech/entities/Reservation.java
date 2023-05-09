package com.eratech.entities;

import java.util.Date;

public class Reservation {

    private int id;
    private Utilisateur utilisateur;
    private Bonplan bonplan;
    private int nombrePersonnes;
    private Date date;
    private String statut;

    public Reservation() {
    }

    public Reservation(int id, Utilisateur utilisateur, Bonplan bonplan, int nombrePersonnes, Date date, String statut) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.bonplan = bonplan;
        this.nombrePersonnes = nombrePersonnes;
        this.date = date;
        this.statut = statut;
    }

    public Reservation(Utilisateur utilisateur, Bonplan bonplan, int nombrePersonnes, Date date, String statut) {
        this.utilisateur = utilisateur;
        this.bonplan = bonplan;
        this.nombrePersonnes = nombrePersonnes;
        this.date = date;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Bonplan getBonplan() {
        return bonplan;
    }

    public void setBonplan(Bonplan bonplan) {
        this.bonplan = bonplan;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


}