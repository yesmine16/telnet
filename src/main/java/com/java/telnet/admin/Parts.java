package com.java.telnet.admin;

import com.google.zxing.WriterException;
import com.java.telnet.DB;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_parts;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Parts implements Initializable {


    @FXML
    private TableColumn<Get_parts, String> access;

    @FXML
    private TableColumn<Get_parts, String> classification;

    @FXML
    private TableColumn<Get_parts, String> comment;

    @FXML
    private HBox crud;

    @FXML
    private TableColumn<Get_parts, Date> date_creation;

    @FXML
    private TableColumn<Get_parts, Date> date_modif;

    @FXML
    private TableColumn<Get_parts, String> desc,cat,av;

    @FXML
    private TableColumn<Get_parts, String> etat;

    @FXML
    private HBox filter;

    @FXML
    private Label filter_btn, name;


    @FXML
    private TableColumn<Get_parts, String> internal_pn;

    @FXML
    private TableColumn<Get_parts, String> label;

    @FXML
    private TableColumn<Get_parts, String> nom;

    @FXML
    private TableColumn<Get_parts, String> modif_by;

    @FXML
    private TableColumn<Get_parts, String> origin;

    @FXML
    private TableColumn<Get_parts, String> param;

    @FXML
    private TableColumn<Get_parts, String> part_numb;

    @FXML
    private TableColumn<Get_parts, String> projet;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Pane desc_pane;

    @FXML
    private ScrollPane description;

    @FXML
    private TableColumn<Get_parts, String> soft;

    @FXML
    private FontAwesomeIconView close;
    @FXML
    private FontAwesomeIconView open;
    @FXML
    private FontAwesomeIconView eye;
    @FXML
    private ImageView img;
    @FXML
    private TableColumn<Get_parts, String> stock;

    @FXML
    private TableColumn<Get_parts, String> storage;

    @FXML
    private TableView<Get_parts> table;

    @FXML
    private ContextMenu menu;
    @FXML
    private BorderPane content;

    @FXML
    private AnchorPane child;

    ObservableList<Get_parts> list = FXCollections.observableArrayList();
    ObservableList<Get_parts> list2 = FXCollections.observableArrayList();


    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("add_part.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
    }

    void load() {
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from ressources");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Get_parts(rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getTimestamp(14), rs.getTimestamp(15), rs.getString(12), rs.getString(13), rs.getString(10), rs.getString(11), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20)));
                table.setItems(list);

            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void menu() {
        MenuItem menuItem1 = new MenuItem("Modifier");
        MenuItem menuItem2 = new MenuItem("Supprimer");
        MenuItem menuItem3 = new MenuItem("Ajouter à liste des achats");
        menu.getItems().addAll(menuItem1, menuItem2, menuItem3);

        filter_btn.setOnMouseClicked(event -> {
            try {
                if (filter.isVisible()) {
                    filter.setVisible(false);
                    scroll.setPrefHeight(80.0);
                } else {
                    filter.setVisible(true);
                    scroll.setPrefHeight(240.0);

                }

            } catch (Exception ex) {

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu();
        filter.setVisible(false);
        scroll.setPrefHeight(80.0);
        child.setPrefHeight(70.0);
        AnchorPane.setRightAnchor(content, 0.0);
        description.setVisible(false);
        open.setOnMouseClicked(event -> {
            open.setVisible(false);
            close.setVisible(true);
            AnchorPane.setRightAnchor(content, 250.0);
            description.setVisible(true);

        });
        close.setOnMouseClicked(event -> {
            close.setVisible(false);
            open.setVisible(true);
            AnchorPane.setRightAnchor(content, 0.0);
            description.setVisible(false);
        });


        internal_pn.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("internal_pn"));
        nom.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("name"));
        label.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("label"));
        classification.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("classification"));
        access.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("access"));
        origin.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("origin"));
        projet.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("project"));
        storage.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("storage"));
        modif_by.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("modified_by"));
        comment.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("comment"));
        etat.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("stat"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("description"));
        soft.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("soft_version"));
        param.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("parametre"));
        stock.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("stock"));
        av.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("av"));
        cat.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("cat"));

        load();
        table.setOnMouseClicked(eventHandler -> {
            eye.setVisible(false);
            for (Get_parts list : table.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {



                    list2.clear();
                    eye.setVisible(false);
                    description.setVisible(true);
                    open.setVisible(false);
                    name.setText(list.getName());
                    list2.add(new Get_parts(list.getInternal_pn(), list.getName(), list.getLabel(), list.getClassification(), list.getAccess(), list.getOrigin(), list.getProject(), list.getStorage(), list.getCreated_on(), list.getModified_on(), list.getModified_by(), list.getComment(), list.getStat(), list.getDescription(), list.getSoft_version(), list.getParametre(), list.getStock(),null,null));
                }


            }
        });


    }


}
