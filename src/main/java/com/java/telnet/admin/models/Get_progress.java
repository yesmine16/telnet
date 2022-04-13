package com.java.telnet.admin.models;

public class Get_progress {
    Integer id,qty;
    String date,stat,resp,comment,projet,update;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public Get_progress(Integer id, Integer qty, String date, String update, String stat, String resp, String comment, String projet) {
        this.id = id;
        this.qty = qty;
        this.date = date;
        this.update=update;
        this.stat = stat;
        this.resp = resp;
        this.comment = comment;
        this.projet = projet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }
}
