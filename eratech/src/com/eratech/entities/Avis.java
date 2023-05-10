package com.eratech.entities;

import java.util.Date;

public class Avis {

    private int id;
    private Utilisateur utilisateur;
    private Bonplan bonplan;
    private float note;
    private Date date;

    public Avis() {
    }

    public Avis(int id, Utilisateur utilisateur, Bonplan bonplan, float note, Date date) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.bonplan = bonplan;
        this.note = note;
        this.date = date;
    }

    public Avis(Utilisateur utilisateur, Bonplan bonplan, float note, Date date) {
        this.utilisateur = utilisateur;
        this.bonplan = bonplan;
        this.note = note;
        this.date = date;
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

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}