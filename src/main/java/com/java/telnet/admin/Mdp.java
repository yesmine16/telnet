package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Mdp implements Initializable {
    @FXML
    private PasswordField pwd;

    @FXML
    private PasswordField repwd;
    @FXML
    private Button valider;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valider.setOnMouseClicked(event -> {
            DB db = new DB();
            if (pwd.getText().equals(repwd.getText()) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Non conforme");
                alert.show();
            } else {
                try {
                    CallableStatement update = db.connect().prepareCall("call update_pwd(?,?)");
                    update.setInt(2, LoginController.id);
                    update.setString(1, pwd.getText());
                    update.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Mot de passe a été changer");
                    alert.show();
                    Node node = (Node) event.getSource();
                    Stage thisStage = (Stage) node.getScene().getWindow();
                    thisStage.close();
                    update.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
