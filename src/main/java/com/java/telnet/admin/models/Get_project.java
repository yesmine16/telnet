package com.java.telnet.admin.models;



public class Get_project {
    String nom,id;
    String desc;
    String etat;
    String tarif;
    String date;
    String created_by;
    String team;
    String comment;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String disc) {
        this.desc = disc;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public Get_project(String id,String nom,String desc,String tarif,String date,String created_by,String team,String comment) {
        this.id=id;
        this.nom = nom;
        this.desc=desc;
        this.tarif=tarif;
        this.date=date;
        this.created_by=created_by;
        this.team=team;
        this.comment=comment;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
