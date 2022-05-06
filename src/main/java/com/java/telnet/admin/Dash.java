package com.java.telnet.admin;

import com.java.telnet.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Dash implements Initializable {

    @FXML
    private Label achat;


    @FXML
    private VBox list;

    @FXML
    private Label projet;

    @FXML
    private Label stock;

    @FXML
    private VBox user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();

        try {
            PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stock.setText(String.valueOf(rs.getInt(1)));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = db.connect().prepareStatement("select count(*) from projet");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                projet.setText(String.valueOf(rs.getInt(1)));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = db.connect().prepareStatement("select count(*) from \"purchase_list \"");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                achat.setText(String.valueOf(rs.getInt(1)));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = db.connect().prepareStatement("select name,date from projet");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                load_project(rs.getTimestamp(2).toString(), rs.getString(1));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        load_user();

    }
    public void load_project(String date, String nom) {

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(100);

        Label lb = new Label();
        lb.setFont(new Font("Lucida Console", 20));
        Label lb1 = new Label();
        lb1.setFont(new Font("Lucida Console", 20));


        lb.setText(date);
        lb1.setText(nom);
        hbox.getChildren().addAll(lb, lb1);
        hbox.getStyleClass().add("test");

        list.getChildren().add(hbox);


    }

    public void load_user() {

        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from login_info");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setSpacing(100);
                Circle avatar = new Circle();
                avatar.setRadius(30.0);
                Label lb = new Label();
                lb.setFont(new Font("Lucida Console", 15));
                Label lb1 = new Label();
                lb1.setFont(new Font("Lucida Console", 15));
                load(avatar, rs);
                lb.setText(rs.getString(2));
                lb1.setText(rs.getString(6));
                hbox.getChildren().addAll(avatar,lb, lb1);        hbox.getStyleClass().add("test");

                user.getChildren().add(hbox);

            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public void load(Circle avatar, ResultSet rs) {
        try {
            InputStream is = rs.getBinaryStream("photo");
            OutputStream os = new FileOutputStream(new File("photo.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();
            Image imagex = new Image("file:photo.jpg", 250, 250, true, true);
            avatar.setFill(new ImagePattern(imagex));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
