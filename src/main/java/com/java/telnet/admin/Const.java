package com.java.telnet.admin;

public class Const {
     String nom;
      String etat;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Const(String nom, String etat) {
        this.nom = nom;
        this.etat = etat;

    }

}
