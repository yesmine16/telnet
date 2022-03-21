package com.java.telnet;

import com.java.telnet.admin.Main_page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LoginController {
public static Integer id;
    @FXML
    private TextField matricule;

    @FXML
    private PasswordField password;


    @FXML
    void loginHandler(ActionEvent event) {
        DB db=new DB();
        try {
            CallableStatement login = db.connect().prepareCall("{ ? = call login(?,?) }");
            login.setString(2, matricule.getText());
            login.setString(3, password.getText());
            login.registerOutParameter(1,Types.INTEGER);
            login.execute();
            CallableStatement statut = db.connect().prepareCall("{ ? = call user_type(?) }");
            statut.setInt(2,login.getInt(1));
            statut.registerOutParameter(1,Types.VARCHAR);
            statut.execute();
            id=login.getInt(1);
            if (login.getInt(1)!=0){
                if(statut.getString(1).equals("admin")){
                try {
                    login.close();
                    statut.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("Main_page.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();


                } catch (IOException ex) {
                }
            }}


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}