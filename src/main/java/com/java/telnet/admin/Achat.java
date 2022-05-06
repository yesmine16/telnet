package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Get_achat;
import com.java.telnet.admin.models.Get_parts;
import com.java.telnet.admin.models.Get_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Achat implements Initializable {
    @FXML
    private TableColumn<Get_achat, String> date;

    @FXML
    private TableColumn<Get_achat, String> desc;

    @FXML
    private TableColumn<Get_achat, String> nom,resp;
    @FXML
    private TableColumn<Get_achat, Label> action;

    @FXML
    private TableColumn<Get_achat, Integer> qty;

    @FXML
    private TableView<Get_achat> table;
    ObservableList<Get_achat> list = FXCollections.observableArrayList();

    public Label action() {
        Label label = new Label();
        label.setText("Valider");
        label.setCursor(Cursor.HAND);
        label.setTextFill(Color.web("#7C90AD"));
        label.setFont(Font.font("system", FontWeight.BOLD, 15));

        return label;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nom.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("nom"));
        qty.setCellValueFactory(new PropertyValueFactory<Get_achat, Integer>("qty"));
        date.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("date"));
        resp.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("resp"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("desc"));
        action.setCellValueFactory(new PropertyValueFactory<Get_achat, Label>("action"));



        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from \"purchase_list \"");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Label l = action();
                list.add(new Get_achat(rs.getString(2), rs.getInt(3), rs.getTimestamp(5).toString(), rs.getString(4), rs.getString(6),rs.getInt(7), l));
                table.setItems(list);
            }
            table.setOnMouseClicked(eventHandler -> {
                for (Get_achat list : table.getSelectionModel().getSelectedItems()) {
                    for (int i = 1; i <= 1; i++) {
                        list.getAction().setOnMouseClicked(event -> {
                            FXMLLoader loader = new FXMLLoader();
                            try {
                                ScrollPane pane = loader.load(getClass().getResource("add_part.fxml").openStream());
                                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                Stage stage = new Stage();
                                stage.setTitle("Nouveau composant");
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setScene(new Scene(pane));
                                stage.show();


                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }

                        });

                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void add(){
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane pane = loader.load(getClass().getResource("buy.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter une nouvel achat");
            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            stage.setScene(new Scene(pane));
            stage.show();


        } catch (IOException e) {
        }
    }
}
