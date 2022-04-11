package com.java.telnet.admin.models;

import javafx.scene.image.ImageView ;
import javafx.scene.layout.HBox;


public class Get_user {
    Integer id;
    String matricule,phone,email;

    public void setId(Integer id) {
        this.id = id;
    }

    public  String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String nom;
     String user;
    ImageView photo;
    String statut;
    String date;

    public HBox getAction() {
        return action;
    }

    public void setAction(HBox action) {
        this.action = action;
    }

    HBox action;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Get_user(Integer id, String matricule, ImageView photo, String date, String nom, String user, HBox action, String phone, String email) {
        this.id = id;
        this.matricule = matricule;
        this.photo=photo;
        this.date=date;
        this.nom=nom;
        this.user=user;
        this.action=action;
        this.phone=phone;
        this.email=email;



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
