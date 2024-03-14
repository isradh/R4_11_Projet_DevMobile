package com.example.r4_11_devmobile;

import java.util.Date;

public class Reservation {
    private String equipement;
    private String dateReservation;
    private String heureDebut;
    private String heureFin;
    private int id;

    public Reservation(int id, String equipement, String dateReservation, String heureDebut, String heureFin) {
        this.id = id;
        this.equipement = equipement;
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public int getId() {
        return id;
    }

    public String getEquipement() {
        return equipement;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public String getDateReservation() {
        return dateReservation;
    }



    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }
}

