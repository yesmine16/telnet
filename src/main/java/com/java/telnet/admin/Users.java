package com.java.telnet.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class Users implements Initializable {

    @FXML
    private Pane panel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panel.getStyleClass().add("panel-primary");

    }



}
