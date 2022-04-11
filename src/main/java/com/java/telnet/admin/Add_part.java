package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Get_parts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Add_part implements Initializable {
    @FXML
    private ComboBox<String> access;

    @FXML
    private ChoiceBox<String> cat;

    @FXML
    private ChoiceBox<String> classification;

    @FXML
    private TextField id;

    @FXML
    private ImageView img;

    @FXML
    private HBox label;

    @FXML
    private TextField nom;

    @FXML
    private ChoiceBox<String> origine;



    @FXML
    private ChoiceBox<String> store;

    public void add_desc() {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane pane = loader.load(getClass().getResource("desc_part.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cat.getItems().addAll("Matériel", "Logiciel", "Bureautique");
        classification.getItems().addAll("Restreinte", "Usage interne");
        origine.getItems().addAll("Externe", "Interne");
        access.getItems().addAll("Team members");
System.out.println(Users.getList());
        cat.setOnAction(event -> {
            if (cat.getSelectionModel().isSelected(0)) label.setDisable(false);
            else label.setDisable(true);
        });
    }
}
