package com.java.telnet;

import com.java.telnet.admin.Main_page;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController extends Application implements Initializable {
    public static Integer id;
    @FXML
    private TextField matricule;

    @FXML
    private PasswordField password;
    @FXML
    private Text actiontarget;

    @FXML
    private Text actiontarget1;
    public static String name;
    static String[] s = new String[7];

    @FXML
    void handle(ActionEvent event) {
        getHostServices().showDocument("mailto:" + "admin@groupe-telnet.net?subject=Problème%20de%20connexion");
    }

    public static String[] getS() {
        return s;
    }

    @FXML
    void loginHandler(ActionEvent event) {

            DB db = new DB();

            final ContextMenu usernameValidator = new ContextMenu();
            usernameValidator.setAutoHide(false);
            final ContextMenu passValidator = new ContextMenu();
            passValidator.setAutoHide(false);
            try {
                CallableStatement login = db.connect().prepareCall("{ ? = call login(?,?) }");
                login.setString(2, matricule.getText());
                login.setString(3, password.getText());
                login.registerOutParameter(1, Types.INTEGER);
                login.execute();
                PreparedStatement ps = db.connect().prepareStatement("SELECT \"user\",nom FROM login_info WHERE id = ?");
                ps.setInt(1, login.getInt(1));
                ResultSet rs = ps.executeQuery();
                PreparedStatement pss = db.connect().prepareStatement("SELECT \"table\", users, parts, projects, storage, history, buy FROM privilege WHERE id = ?");
                pss.setInt(1, login.getInt(1));
                ResultSet rss = pss.executeQuery();
                while (rss.next()) {

                    for (int i = 1; i <= 7; i++) {
                        s[i - 1] = rss.getArray(i).toString();

                    }
                }
                if (rs.next()) {
                    name = rs.getString(2);
                }
                id = login.getInt(1);

                if (matricule.getText().equals("")) {
                    actiontarget.setVisible(true);
                    actiontarget.setText("champ obligatoire");

                }
                if (password.getText().equals("")) {
                    actiontarget1.setVisible(true);
                    actiontarget1.setText("champ obligatoire");
                } else {
                    if (login.getInt(1) != 0) {
                        try {
                            login.close();
                            ps.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("Main_page.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            Node node = (Node) event.getSource();
                            Stage thisStage = (Stage) node.getScene().getWindow();
                            thisStage.close();

                        } catch (IOException ex) {
                        }

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("veuillez vérifier votre matricule ou mot de passe");
                        alert.show();
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();

        }
    }

    @Override
    public void start(Stage st) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
matricule.setText("12");
password.setText("0000");


    }
}