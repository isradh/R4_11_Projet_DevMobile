package com.example.r4_11_devmobile;

public class Resident {
    private String id;
    private String nom;
    private String prenom;
    private int etage;
    private int nombreEquipement;

    private int superficie;

    private int UserID;


    // Constructeur
    public Resident( String nom, String prenom, int nombreEquipement, int etage, int superficie) {

        this.nom = nom;
        this.prenom = prenom;
        this.nombreEquipement = nombreEquipement;
        this.etage = etage;
        this.superficie = superficie;
    }

    public Resident(String id, String nom, String prenom, int nombreEquipement, int etage) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nombreEquipement = nombreEquipement;
        this.etage = etage;
    }

    public Resident(String UserID){
        this.id = UserID;
    }

    // Méthodes d'accès aux données
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNombreEquipement() {
        return nombreEquipement;
    }

    public int getEtage() {
        return etage;
    }

    public int getSuperficie() {
        return superficie;
    }

}
