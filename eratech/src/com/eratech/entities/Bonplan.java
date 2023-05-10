package com.eratech.entities;

public class Bonplan {

    private int id;
    private String titre;
    private String description;
    private String type;
    private String image;

    public Bonplan() {
    }

    public Bonplan(int id, String titre, String description, String type, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.image = image;
    }

    public Bonplan(String titre, String description, String type, String image) {
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}