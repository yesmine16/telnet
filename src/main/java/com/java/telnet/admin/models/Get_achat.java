package com.java.telnet.admin.models;

import javafx.scene.control.Label;

public class Get_achat {
    String nom;
    int qty;
    String date;
    String desc;
    Label action;
    String resp;

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public Get_achat(String nom, int qty, String date, String desc, String resp, Label action) {
        this.nom = nom;
        this.qty = qty;
        this.date = date;
        this.desc = desc;
        this.resp=resp;
        this.action = action;
    }

    public Label getAction() {
        return action;
    }

    public void setAction(Label action) {
        this.action = action;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
