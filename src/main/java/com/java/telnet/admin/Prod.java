package com.java.telnet.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class Prod {
    String id_prod, projet_prod, start, update_date, avan, param, qty_prod, soft, test, add_by;
    HBox action;

    public Prod(String id_prod, String projet_prod, String start, String update_date, String avan, String param, String qty_prod, String soft, String test, String add_by, HBox action) {
        this.id_prod = id_prod;
        this.projet_prod = projet_prod;
        this.start = start;
        this.update_date = update_date;
        this.avan = avan;
        this.param = param;
        this.qty_prod = qty_prod;
        this.soft = soft;
        this.test = test;
        this.add_by = add_by;
        this.action = action;
    }

    public String getId_prod() {
        return id_prod;
    }

    public void setId_prod(String id_prod) {
        this.id_prod = id_prod;
    }

    public String getProjet_prod() {
        return projet_prod;
    }

    public void setProjet_prod(String projet_prod) {
        this.projet_prod = projet_prod;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getAvan() {
        return avan;
    }

    public void setAvan(String avan) {
        this.avan = avan;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getQty_prod() {
        return qty_prod;
    }

    public void setQty_prod(String qty_prod) {
        this.qty_prod = qty_prod;
    }

    public String getSoft() {
        return soft;
    }

    public void setSoft(String soft) {
        this.soft = soft;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getAdd_by() {
        return add_by;
    }

    public void setAdd_by(String add_by) {
        this.add_by = add_by;
    }
}
