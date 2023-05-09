package com.eratech.entities;

import com.eratech.utils.Statics;

public class Quiz implements Comparable<Quiz> {

    private int id;
    private String nom;
    private String difficulte;
    private String image;

    public Quiz() {
    }

    public Quiz(int id, String nom, String difficulte, String image) {
        this.id = id;
        this.nom = nom;
        this.difficulte = difficulte;
        this.image = image;
    }

    public Quiz(String nom, String difficulte, String image) {
        this.nom = nom;
        this.difficulte = difficulte;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "\n Nom du quizz : " + nom +
                "\n Difficulte : " + difficulte;
    }

    @Override
    public int compareTo(Quiz quiz) {
        switch (Statics.compareVar) {
            case "Nom":
                return this.getNom().compareTo(quiz.getNom());
            case "Difficulte":
                return this.getDifficulte().compareTo(quiz.getDifficulte());
            default:
                return 0;
        }
    }

}