package com.java.telnet.admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Projet implements Initializable {


    @FXML
     TableColumn<Const, String> nom;

    @FXML
     TableColumn<Const, String> etat;

    @FXML
    private FontAwesomeIconView eye;

    @FXML
    private Label nom_projet;

    @FXML
    private TabPane tab_pane;

    @FXML
     TableView<Const> table;
    @FXML
    private HBox titre;


ObservableList<Const> list=FXCollections.observableArrayList(
        new Const("projet1","cv"),
        new Const("projet2","cv")
);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            nom.setCellValueFactory(new PropertyValueFactory<Const, String>("nom"));
            etat.setCellValueFactory(new PropertyValueFactory<Const, String>("etat"));
            table.setItems(list);



        if(table.getSelectionModel().isEmpty()){
            eye.setVisible(true);
            tab_pane.setVisible(false);
            titre.setVisible(false);
        }
        else{ eye.setVisible(false);
            tab_pane.setVisible(true);
            titre.setVisible(true);}





    }


}
