package com.java.telnet.admin;

import com.google.zxing.WriterException;
import com.java.telnet.DB;
import com.java.telnet.LoginController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProject implements Initializable {


    @FXML
    private TextArea descr;

    @FXML
    private TextField id;

    @FXML
    private TextField nom;

    @FXML
    private TextField notes;

    @FXML
    private TextField resp;
    @FXML
    private Button submit, update;
    @FXML
    private TextField tarif;
    @FXML
    private Text txt1;

    @FXML
    private Text txt2;

    @FXML
    private Text txt3, txt4;

    public void update() throws SQLException {

        if (id.getText().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;


        }
        if (nom.getText().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;


        }
        if (tarif.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;

        }
        if (resp.getText().isEmpty()) {
            txt4.setVisible(true);
            txt4.setManaged(true);
            v = false;


        }

        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("UPDATE projet SET  name=?, \"description \"=?, pricing=?, created_by=?, team=?, notes=? WHERE id=?;");
            ps.setString(7, Projet.list3.get(0).getId());
            ps.setString(1, nom.getText());
            ps.setString(2, descr.getText());
            ps.setString(3, tarif.getText());
            ps.setString(4, LoginController.name);
            ps.setString(5, resp.getText());
            ps.setString(6, notes.getText());

            ps.executeUpdate();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'utilisateur  a été mis à jour avec succes");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void submit() throws SQLException {

        if (id.getText().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;


        }
        if (nom.getText().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;


        }
        if (tarif.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;

        }
        if (resp.getText().isEmpty()) {
            txt4.setVisible(true);
            txt4.setManaged(true);
            v = false;


        }
        if (verif == false) {
            txt1.setText("Id existe déjà");
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;

        }
        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("INSERT INTO public.projet(name, \"description \", pricing,  created_by, team, notes,id) VALUES (?, ?, ?, ?, ?, ?,?);");
            ps.setString(1, nom.getText());
            ps.setString(2, descr.getText());
            ps.setString(3, tarif.getText());
            ps.setString(4, LoginController.name);
            ps.setString(5, resp.getText());
            ps.setString(6, notes.getText());
            ps.setString(7, id.getText());

            ps.executeUpdate();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'utilisateur  a été mis à jour avec succes");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
        }
    }

    Boolean verif = true;
    Boolean v = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt1.setManaged(false);
        txt2.setManaged(false);
        txt3.setManaged(false);
        txt4.setManaged(false);

        submit.setOnMouseClicked(event -> {
            if (Projet.list3.isEmpty()) {
                id.focusedProperty().addListener((observableValue, old, newval) -> {
                    if (old) {
                        DB db = new DB();
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("select id from projet where id=?");
                            ps.setString(1, id.getText());
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                txt1.setVisible(true);
                                txt1.setManaged(true);
                                txt1.setText("Id existe déjà");
                                verif = false;

                            } else {
                                txt1.setVisible(false);
                                txt1.setManaged(false);
                                txt1.setText("champ obligatoire !");
                            }
                        } catch (Exception e) {
                        }
                    }

                });
            }
        });
        if (Projet.list3.isEmpty() == false) {
            id.setText(Projet.list3.get(0).getId());
            nom.setText(Projet.list3.get(0).getNom());
            tarif.setText(Projet.list3.get(0).getTarif());
            resp.setText(Projet.list3.get(0).getCreated_by());
            descr.setText(Projet.list3.get(0).getDesc());
            notes.setText(Projet.list3.get(0).getComment());
            submit.setVisible(false);
            update.setVisible(true);
            submit.setManaged(false);
        } else {
            update.setVisible(false);
            submit.setVisible(true);
            update.setManaged(false);
        }
    }

}
