package com.java.telnet.admin;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Users  implements Initializable  {
    @FXML
    private AnchorPane main;

    @FXML
    private TableView<?> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    Dashboard d=new Dashboard();

    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("add_user.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            main.setEffect(new BoxBlur(5.0, 5.0, 1));


            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    main.setEffect(new BoxBlur(0, 0, 0));
                }
            });


        } catch (IOException ex) {
        }


    }


}
