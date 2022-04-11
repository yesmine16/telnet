package com.java.telnet.admin.models;

public class Bom {
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    String num,label,desc,design,comment,resp,date,nom,etat;
Integer qty,stock,to_buy;

    public Bom(String num,String nom, String label, String desc, String design, String comment, String resp, String date, Integer qty, Integer stock, Integer to_buy,String etat) {
        this.num = num;
        this.nom=nom;
        this.label = label;
        this.desc = desc;
        this.design = design;
        this.comment = comment;
        this.resp = resp;
        this.date = date;
        this.qty = qty;
        this.stock = stock;
        this.to_buy = to_buy;
        this.etat=etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTo_buy() {
        return to_buy;
    }

    public void setTo_buy(Integer to_buy) {
        this.to_buy = to_buy;
    }
}
