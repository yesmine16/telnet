package com.java.telnet.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class Parametre implements Initializable {

    @FXML
    private HBox change;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change.setOnMouseClicked(event->{
            FXMLLoader loader = new FXMLLoader();
            try {

                Pane pane = loader.load(getClass().getResource("mdp.fxml").openStream());
                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                Stage stage = new Stage();
                stage.setScene(new Scene(pane));
                stage.show();
                Node node = (Node) event.getSource();
                Stage thisStage = (Stage) node.getScene().getWindow();
                thisStage.close();
            } catch (IOException e) {
            }
        });
    }

}
