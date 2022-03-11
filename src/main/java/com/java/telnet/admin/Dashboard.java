package com.java.telnet.admin;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private StackPane content;
    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private Circle avatar;

    @FXML
    private HBox dashboard;

    @FXML
    private HBox parts;

    @FXML
    private HBox project;

    @FXML
    private ScrollPane slider;

    @FXML
    private HBox stock;

    @FXML
    private HBox users;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();



            slider.setTranslateX(-320);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-320);
            slide.play();
            slide.setNode(content);
            slide.setToX(-320);
            slide.play();
            slider.setTranslateX(0);


            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });

            Image im = new Image("file:src/main/resources/com/java/telnet/admin/user.jpg",false);
            avatar.setFill(new ImagePattern(im));

       dashboard.setOnMouseClicked(event -> {try {
            Parent fxml = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            content.getChildren().removeAll();
            content.getChildren().setAll(fxml);

        }catch (IOException ex){

        }});
        project.setOnMouseClicked(event -> {try {
            Parent fxml = FXMLLoader.load(getClass().getResource("project.fxml"));
            content.getChildren().removeAll();
            content.getChildren().setAll(fxml);

        }catch (IOException ex){

        }});
        users.setOnMouseClicked(event -> {try {
            Parent fxml = FXMLLoader.load(getClass().getResource("users.fxml"));
            content.getChildren().removeAll();
            content.getChildren().setAll(fxml);

        }catch (IOException ex){

        }});
        parts.setOnMouseClicked(event -> {try {
            Parent fxml = FXMLLoader.load(getClass().getResource("parts.fxml"));
            content.getChildren().removeAll();
            content.getChildren().setAll(fxml);

        }catch (IOException ex){

        }});
        stock.setOnMouseClicked(event -> {try {
            Parent fxml = FXMLLoader.load(getClass().getResource("stock.fxml"));
            content.getChildren().removeAll();
            content.getChildren().setAll(fxml);

        }catch (IOException ex){

        }});

    }

    }

     


