package com.java.telnet.admin.models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Get_project {
    String nom,id;
    String desc;
    String date;
    String created_by;
    String team;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String disc) {
        this.desc = disc;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public Get_project(String id,String nom,String desc,String date,String created_by,String team) {
        this.id=id;
        this.nom = nom;
        this.desc=desc;
        this.date=date;
        this.created_by=created_by;
        this.team=team;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
