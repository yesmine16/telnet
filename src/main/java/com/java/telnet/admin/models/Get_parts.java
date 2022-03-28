package com.java.telnet.admin.models;

import java.sql.Timestamp;

public class Get_parts {
   String internal_pn,part_number, name, label, classification, access, origin, project, storage,  modified_by, comment,stat,description, soft_version, parametre,stock;
   Timestamp created_on, modified_on;

    public Get_parts(String internal_pn, String part_number, String name, String label, String classification, String access, String origin, String project, String storage,Timestamp created_on, Timestamp modified_on, String modified_by, String comment, String stat, String description, String soft_version, String parametre, String stock) {
        this.internal_pn = internal_pn;
        this.part_number = part_number;
        this.name = name;
        this.label = label;
        this.classification = classification;
        this.access = access;
        this.origin = origin;
        this.project = project;
        this.storage = storage;
        this.created_on=created_on;
        this.modified_on=modified_on;
        this.modified_by = modified_by;
        this.comment = comment;
        this.stat = stat;
        this.description = description;
        this.soft_version = soft_version;
        this.parametre = parametre;
        this.stock = stock;

    }

    public String getInternal_pn() {
        return internal_pn;
    }

    public void setInternal_pn(String internal_pn) {
        this.internal_pn = internal_pn;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProject() {
        return project;
    }

    public void setProject_id(String project) {
        this.project = project;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription_id(String description) {
        this.description = description;
    }

    public String getSoft_version() {
        return soft_version;
    }

    public void setSoft_version(String soft_version) {
        this.soft_version = soft_version;
    }

    public String getParametre() {
        return parametre;
    }

    public void setParametre(String parametre) {
        this.parametre = parametre;
    }

    public String getStock() {
        return stock;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Timestamp getModified_on() {
        return modified_on;
    }

    public void setModified_on(Timestamp modified_on) {
        this.modified_on = modified_on;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }}


