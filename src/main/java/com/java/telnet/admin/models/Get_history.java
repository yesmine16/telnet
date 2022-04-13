package com.java.telnet.admin.models;

public class Get_history {
    String part_id,resp,date,event;
    Integer id;

    public Get_history(String part_id, String resp, String date, String event, Integer id) {
        this.part_id = part_id;
        this.resp = resp;
        this.date = date;
        this.event = event;
        this.id = id;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
