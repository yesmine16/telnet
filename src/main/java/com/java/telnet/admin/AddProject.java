package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

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
    private TextField nom;


    @FXML
    private AnchorPane resp;
    @FXML
    private Button submit, update;


    @FXML
    private Text txt2;

    @FXML
    private Text txt4;

    public void update() throws SQLException {


        if (nom.getText().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;


        }


        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("UPDATE projet SET  name=?, \"desc\"=?, resp=?, team=? WHERE id=?;");
            ps.setString(5, Projet.list3.get(0).getId());
            ps.setString(1, nom.getText());
            ps.setString(2, descr.getText());
            ps.setString(3, LoginController.name);
            ps.setString(4, controll.getCheckModel().getCheckedItems().toString());

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


        if (nom.getText().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;
        }
        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("INSERT INTO public.projet(name, \"desc\",resp, team)VALUES (?, ?, ?, ?);");
            ps.setString(1, nom.getText());
            ps.setString(2, descr.getText());
            ps.setString(3, LoginController.name);
            ps.setString(4, controll.getCheckModel().getCheckedItems().toString());

            ps.executeUpdate();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'utilisateur  a été mis à jour avec succés");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
        }
    }

    Boolean verif = true;
    Boolean v = true;

    ObservableList<String> items = FXCollections.observableArrayList();
    CheckComboBox<String> controll = new CheckComboBox<String>(items);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt2.setManaged(false);
        txt4.setManaged(false);


        if (Projet.list3.isEmpty() == false) {
            nom.setText(Projet.list3.get(0).getNom());
            descr.setText(Projet.list3.get(0).getDesc());
            submit.setVisible(false);
            update.setVisible(true);
            submit.setManaged(false);
        } else {
            update.setVisible(false);
            submit.setVisible(true);
            update.setManaged(false);
        }
        try {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("select nom from login_info");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(rs.getString(1));
            }
            ps.close();
            resp.getChildren().add(controll);
            controll.setLayoutX(300.0);
            controll.setLayoutY(50.0);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
