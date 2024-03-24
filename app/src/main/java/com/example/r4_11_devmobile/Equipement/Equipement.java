package com.example.r4_11_devmobile.Equipement;

public class Equipement {

    private int id;


    private String nom;
    private int wattage;
    private String imageUrl;


    public Equipement(int id, String nom, int wattage, String imageUrl){
        this.id = id;
        this.nom = nom;
        this.wattage = wattage;
        this.imageUrl = imageUrl; // Initialisez l'URL de l'image
    }


    public int getId(){
        return id;
    }
    public String getNom() {
        return nom;
    }

    public int getWattage() {
        return wattage;
    }


    public String getImageUrl() {
        return imageUrl;
    }
}
