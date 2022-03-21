package com.java.telnet.admin;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Parts implements Initializable {


    @FXML
    private ContextMenu menu;
    @FXML
    private HBox filter;

    @FXML
    private Label filter_btn;




    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("add_part.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();




        } catch (IOException ex) {
        }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MenuItem menuItem1 = new MenuItem("Modifier");
        MenuItem menuItem2 = new MenuItem("Supprimer");
        MenuItem menuItem3 = new MenuItem("Ajouter à liste des achats");
        menu.getItems().addAll(menuItem1,menuItem2,menuItem3);
        
        filter_btn.setOnMouseClicked(event -> {try {
         if(filter.isVisible()){filter.setVisible(false);}
         else {filter.setVisible(true);

         }

        }catch (Exception ex){

        }});







        }
}
