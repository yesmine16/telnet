package com.java.telnet.admin.models;

import javafx.scene.image.ImageView ;

import java.sql.Timestamp;
import java.util.Date;

public class Get_user {
    Integer id;
    String matricule;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    String nom,user;
    ImageView photo;
    String statut;
    Timestamp date;

    public Get_user(Integer id, String matricule, ImageView photo, Timestamp date, String nom,String user) {
        this.id = id;
        this.matricule = matricule;
        this.photo=photo;
        this.date=date;
        this.nom=nom;
        this.user=user;

    }

    public Get_user() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
