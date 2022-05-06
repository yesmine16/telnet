package com.java.telnet.admin.models;

public class Bom {
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getProjet() {
        return projet;
    }

    public void setProjet(Integer projet) {
        this.projet = projet;
    }


Integer projet;
    String num,label,desc,design,comment,resp,date,nom;
Integer qty,stock,to_buy,id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bom(Integer id, String num, String nom, String label, String design, String comment, String resp, String date, Integer qty, Integer projet) {
        this.id=id;
        this.num = num;
        this.nom=nom;
        this.label = label;
        this.design = design;
        this.comment = comment;
        this.resp = resp;
        this.date = date;
        this.qty = qty;
        this.projet=projet;
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

    public Bom(String date,String resp, String nom, Integer projet, Integer qty) {
        this.date=date;
        this.resp = resp;
        this.nom = nom;
        this.projet = projet;
        this.qty = qty;
    }
}
