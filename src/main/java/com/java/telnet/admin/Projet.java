package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Const;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Projet implements Initializable {


    @FXML
    private TableColumn<Const, String> nom;

    @FXML
    private TableColumn<Const, String> etat;

    @FXML
    private FontAwesomeIconView eye;

    @FXML
    private Pane pane;

    @FXML
    private Label nom_projet;

    @FXML
    private TabPane tab_pane;

    @FXML
    private TableView<Const> table, table2;
    @FXML
    private HBox titre;


    ObservableList<Const> list = FXCollections.observableArrayList(
            new Const("projet1", "cv"),
            new Const("projet2", "cv")
    );
    ObservableList<Const> list2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eye.setVisible(true);
        tab_pane.setVisible(false);
        titre.setVisible(false);
        nom.setCellValueFactory(new PropertyValueFactory<Const, String>("nom"));
        etat.setCellValueFactory(new PropertyValueFactory<Const, String>("etat"));
        table.setItems(list);
        table.setOnMouseClicked(eventHandler -> {
                    eye.setVisible(false);
                    tab_pane.setVisible(true);
                    titre.setVisible(true);
                    for (Const list : table.getSelectionModel().getSelectedItems()) {
                        for (int i = 1; i <= 1; i++) {
                            list2.clear();
                            nom_projet.setText(list.getNom());
                            list2.add(new Const(list.getNom(), list.getEtat()));
                        }
                        table2.setItems(list2);
                    }

                }

        );


    }


}
