package com.example.r4_11_devmobile;

public class Equipement {


    private String nom;
    private int wattage;


    public Equipement(String nom, int wattage){
        this.nom = nom;
        this.wattage = wattage;
    }

    public String getNom() {
        return nom;
    }

    public int getWattage() {
        return wattage;
    }
}
