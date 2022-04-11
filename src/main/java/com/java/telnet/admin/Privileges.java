package com.java.telnet.admin;

import com.java.telnet.DB;

import com.java.telnet.admin.models.Get_user;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Privileges implements Initializable {


    @FXML
    private ChoiceBox<String> buy;

    @FXML
    private ToggleGroup grp;

    @FXML
    private ToggleGroup grp2;

    @FXML
    private ToggleGroup grp3;

    @FXML
    private ToggleGroup grp4;

    @FXML
    private ToggleGroup grp5;

    @FXML
    private ToggleGroup grp6;

    @FXML
    private ToggleGroup grp7;

    @FXML
    private ChoiceBox<String> hist;


    @FXML
    private ChoiceBox<String> parts;

    @FXML
    private ChoiceBox<String> projet;


    @FXML
    private ChoiceBox<String> store;

    @FXML
    private ChoiceBox<String> tableau;

    @FXML
    private CheckBox tout;

    @FXML
    private ChoiceBox<String> users;

    @FXML
    private Circle avatar;
    @FXML
    private TextField mail;

    @FXML
    private TextField mat;

    @FXML
    private TextField nom;
    @FXML
    private TextField stat, phone;
    @FXML
    private Button submit;

    public void select() {

        if (tout.isSelected()) {
            grp.getToggles().get(0).setSelected(true);
            grp2.getToggles().get(0).setSelected(true);
            grp3.getToggles().get(0).setSelected(true);
            grp4.getToggles().get(0).setSelected(true);
            grp5.getToggles().get(0).setSelected(true);
            grp6.getToggles().get(0).setSelected(true);
            grp7.getToggles().get(0).setSelected(true);
            users.setVisible(true);
            tableau.setVisible(true);
            hist.setVisible(true);
            buy.setVisible(true);
            store.setVisible(true);
            projet.setVisible(true);
            parts.setVisible(true);
        } else {
            grp.getToggles().get(0).setSelected(false);
            grp2.getToggles().get(0).setSelected(false);
            grp3.getToggles().get(0).setSelected(false);
            grp4.getToggles().get(0).setSelected(false);
            grp5.getToggles().get(0).setSelected(false);
            grp6.getToggles().get(0).setSelected(false);
            grp7.getToggles().get(0).setSelected(false);
            users.setVisible(false);
            tableau.setVisible(false);
            hist.setVisible(false);
            buy.setVisible(false);
            store.setVisible(false);
            projet.setVisible(false);
            parts.setVisible(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        users.getItems().addAll("Lecture", "Lecture et Ecriture");
        users.getSelectionModel().selectFirst();
        tableau.getItems().addAll("Lecture", "Lecture et Ecriture");
        tableau.getSelectionModel().selectFirst();

        hist.getItems().addAll("Lecture", "Lecture et Ecriture");
        hist.getSelectionModel().selectFirst();

        buy.getItems().addAll("Lecture", "Lecture et Ecriture");
        buy.getSelectionModel().selectFirst();

        store.getItems().addAll("Lecture", "Lecture et Ecriture");
        store.getSelectionModel().selectFirst();

        projet.getItems().addAll("Lecture", "Lecture et Ecriture");
        projet.getSelectionModel().selectFirst();

        parts.getItems().addAll("Lecture", "Lecture et Ecriture");
        parts.getSelectionModel().selectFirst();
        submit.setOnMouseClicked(event -> {
//            if (list.isEmpty()) {
//                try {
//                    insert();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                try {
//                    update();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }

        });

    }

    public File file1;

    public void img() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            avatar.setFill(new ImagePattern(image1));
            file1 = new File(file.toURI().getPath());
        }

    }


    public void insert() throws SQLException, FileNotFoundException {
        DB db = new DB();
        PreparedStatement ps = db.connect().prepareStatement("INSERT INTO login_info (matricule, \"user\", photo, nom, email, phone) VALUES ( ?,?,?,?,?,?);");
        PreparedStatement insert = db.connect().prepareStatement("INSERT into privilege(\"table\", users, parts, projects, storage, history, buy)VALUES (?,?,?,?,?,?,?);");
        ps.setString(4, nom.getText());
        ps.setString(1, mat.getText());
        ps.setString(2, stat.getText());
        ps.setString(5, mail.getText());
        ps.setString(6, phone.getText());
        FileInputStream fin = new FileInputStream(file1);
        ps.setBinaryStream(3, fin, (int) file1.length());
        if (grp.getToggles().get(0).isSelected()) {
            if (tableau.getSelectionModel().isSelected(0)) {
                insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp2.getToggles().get(0).isSelected()) {
            if (users.getSelectionModel().isSelected(0)) {
                insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp3.getToggles().get(0).isSelected()) {
            if (parts.getSelectionModel().isSelected(0)) {
                insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp4.getToggles().get(0).isSelected()) {
            if (hist.getSelectionModel().isSelected(0)) {
                insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp5.getToggles().get(0).isSelected()) {
            if (projet.getSelectionModel().isSelected(0)) {
                insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp6.getToggles().get(0).isSelected()) {
            if (store.getSelectionModel().isSelected(0)) {
                insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp7.getToggles().get(0).isSelected()) {
            if (buy.getSelectionModel().isSelected(0)) {
                insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        ps.executeUpdate();

        insert.close();
        ps.close();


    }

    public void update() throws SQLException, FileNotFoundException {
        DB db = new DB();
        PreparedStatement ps = db.connect().prepareStatement("INSERT INTO login_info (matricule, \"user\", photo, nom, email, phone) VALUES ( ?,?,?,?,?,?);");
        PreparedStatement insert = db.connect().prepareStatement("INSERT into privilege(\"table\", users, parts, projects, storage, history, buy)VALUES (?,?,?,?,?,?,?);");
        ps.setString(4, nom.getText());
        ps.setString(1, mat.getText());
        ps.setString(2, stat.getText());
        ps.setString(5, mail.getText());
        ps.setString(6, phone.getText());

        FileInputStream fin = new FileInputStream(file1);
        ps.setBinaryStream(3, fin, (int) file1.length());
        if (grp.getToggles().get(0).isSelected()) {
            if (tableau.getSelectionModel().isSelected(0)) {
                insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp2.getToggles().get(0).isSelected()) {
            if (users.getSelectionModel().isSelected(0)) {
                insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp3.getToggles().get(0).isSelected()) {
            if (parts.getSelectionModel().isSelected(0)) {
                insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp4.getToggles().get(0).isSelected()) {
            if (hist.getSelectionModel().isSelected(0)) {
                insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp5.getToggles().get(0).isSelected()) {
            if (projet.getSelectionModel().isSelected(0)) {
                insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp6.getToggles().get(0).isSelected()) {
            if (store.getSelectionModel().isSelected(0)) {
                insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        if (grp7.getToggles().get(0).isSelected()) {
            if (buy.getSelectionModel().isSelected(0)) {
                insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
            } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
        } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
        ps.executeUpdate();

        insert.close();
        ps.close();


    }
}
