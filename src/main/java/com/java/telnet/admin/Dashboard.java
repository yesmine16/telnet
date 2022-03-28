package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.Login;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_user;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private StackPane content;
    @FXML
    private Label Menu;

    @FXML
    private ScrollPane main_page;

    @FXML
    private Label MenuClose, time;

    @FXML
    private Circle avatar;

    @FXML
    private HBox dashboard;

    @FXML
    private HBox parts;

    @FXML
    private HBox project;
    @FXML
    private HBox achat;
    @FXML
    private HBox history;

    @FXML
    private ScrollPane slider;

    @FXML
    private HBox stock;

    @FXML
    private HBox users;
    @FXML
    private AnchorPane menu;
    @FXML
    private Label name;

    public void load() {
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select photo from login_info where id=?");
            ps.setInt(1, LoginController.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

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


            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //load();
        name.setText(LoginController.name);
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            time.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();
            slide.setNode(main_page);
            AnchorPane.setLeftAnchor(main_page, 320.0);
            slide.play();

            slider.setTranslateX(-320);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide1 = new TranslateTransition();
            slide1.setDuration(Duration.seconds(0.4));
            slide1.setNode(main_page);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-320);
            slide.play();
            slide.setNode(main_page);
            slide.setToX(-320);
            slider.setTranslateX(0);
            AnchorPane.setLeftAnchor(main_page, 25.0);
            slide1.play();
            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });


        try {
            content.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("dashboard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dashboard.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        project.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("project.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        users.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("users.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        parts.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("parts.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        stock.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("stock.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        achat.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("achat.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
        history.setOnMouseClicked(event -> {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("history.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);

            } catch (IOException ex) {

            }
        });
    }


    public void setMenu(boolean x) {
        if (x) {
            menu.setEffect(new BoxBlur(5.0, 5.0, 1));
        } else menu.setEffect(new BoxBlur(0, 0, 0));

    }

    @FXML
    void logout(MouseEvent event) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.setTitle("confirmer votre choix");
            alert.setContentText("Vous etes sur de quitter?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Node node = (Node) event.getSource();
                Stage thisStage = (Stage) node.getScene().getWindow();
                thisStage.close();
                LoginController.id = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

     


