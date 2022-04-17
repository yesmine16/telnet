package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Get_parts;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
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
    private TableColumn<Get_parts, String> date_creation;

    @FXML
    private TableColumn<Get_parts, Date> date_modif;

    @FXML
    private TableColumn<Get_parts, String> desc, cat, av;
    @FXML
    private FontAwesomeIconView qr, pdf;
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
                list.add(new Get_parts(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(7), rs.getString(8), null, rs.getString(10), rs.getTimestamp(11).toString(), null, rs.getString(12), rs.getString(13), rs.getString(18), rs.getString(14), rs.getString(15)));
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
        comment.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("comment"));
        etat.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("stat"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("description"));

        av.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("av"));
        cat.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("cat"));
        date_creation.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("created_on"));

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
                    qr.setOnMouseClicked(e -> {
                        DB db = new DB();
                        PopOver popup = new PopOver();
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("select qr from ressources where internal_pn=?");
                            ps.setString(1, list.getInternal_pn());
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                ImageView imgView2 = img(rs, "qr");
                                imgView2.prefHeight(100.0);
                                imgView2.prefWidth(100.0);
                                popup.setContentNode(imgView2);
                                popup.show(qr);
                                popup.setAutoHide(true);


                            }

                        } catch (Exception exx) {
                        }
                    });
                    pdf.setOnMouseClicked(e -> {
                        String query = "select datasheet from ressources where internal_pn =?";


                        try {
                            DB db = new DB();
                            PreparedStatement preparedStatement = db.connect().prepareStatement(query);
                            preparedStatement.setString(1, list.getInternal_pn());
                            ResultSet rs = preparedStatement.executeQuery();


                            FileOutputStream fos = new FileOutputStream(  list.getName() + " datasheet.pdf");
                            rs.next();
                            byte[] fileBytes = rs.getBytes(1);
                            fos.write(fileBytes);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("fichier enregistré avec succès");
                            alert.show();

                        } catch (Exception ex) {
                        }


                    });

                }


            }
        });


    }

    public ImageView img(ResultSet rs, String name) throws Exception {
        ImageView imgView = new ImageView();
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        imgView.setPreserveRatio(true);
        InputStream is = rs.getBinaryStream(name);
        OutputStream os = new FileOutputStream(new File("photo.jpg"));
        byte[] content = new byte[2048];
        int size = 0;
        while ((size = is.read(content)) != -1) {
            os.write(content, 0, size);
        }
        os.close();
        is.close();
        Image imagex = new Image("file:photo.jpg");
        imgView.setImage(imagex);
        return imgView;
    }


}
