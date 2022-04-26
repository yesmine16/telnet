package com.java.telnet.admin.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.sql.Timestamp;

public class Get_parts {
   String internal_pn, name, label, classification, origin,  storage,description,created_on;
Byte datasheet;
ImageView img,qr;
HBox action;

    public HBox getAction() {
        return action;
    }

    public void setAction(HBox action) {
        this.action = action;
    }


    public Get_parts(ImageView img, String internal_pn, String name, String label, String classification, String origin,  String storage, String created_on,  String description, HBox action,ImageView qr) {
        this.img=img;
        this.internal_pn = internal_pn;
        this.name = name;
        this.label = label;
        this.classification = classification;
        this.origin = origin;
        this.storage = storage;
        this.created_on=created_on;
        this.description = description;
        this.action = action;
        this.qr=qr;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public ImageView getQr() {
        return qr;
    }

    public void setQr(ImageView qr) {
        this.qr = qr;
    }

    public Byte getDatasheet() {
        return datasheet;
    }

    public void setDatasheet(Byte datasheet) {
        this.datasheet = datasheet;
    }

    public String getInternal_pn() {
        return internal_pn;
    }

    public void setInternal_pn(String internal_pn) {
        this.internal_pn = internal_pn;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }



    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }


    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }







    public String getDescription() {
        return description;
    }

    public void setDescription_id(String description) {
        this.description = description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }




    }


