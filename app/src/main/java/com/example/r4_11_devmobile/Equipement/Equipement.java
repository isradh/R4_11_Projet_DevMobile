package com.example.r4_11_devmobile.Equipement;

public class Equipement {

    private int id;


    private String nom;
    private int wattage;


    public Equipement(int id, String nom, int wattage){
        this.id = id;
        this.nom = nom;
        this.wattage = wattage;
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
}
