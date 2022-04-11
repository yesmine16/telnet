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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private TableColumn<Get_achat, String> nom;
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
        label.setStyle("-fxbackground-color:green;");
        label.setCursor(Cursor.HAND);
        return label;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nom.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("nom"));
        qty.setCellValueFactory(new PropertyValueFactory<Get_achat, Integer>("qty"));
        date.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("date"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_achat, String>("desc"));
        action.setCellValueFactory(new PropertyValueFactory<Get_achat, Label>("action"));



        DB db = new DB();
        Label l = action();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from \"purchase_list \"");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.add(new Get_achat(rs.getString(2), rs.getInt(3), rs.getTimestamp(5).toString(), rs.getString(4), null, l));
                table.setItems(list);
            }
            table.setOnMouseClicked(eventHandler -> {
                for (Get_achat list : table.getSelectionModel().getSelectedItems()) {
                    for (int i = 1; i <= 1; i++) {
                        list.getAction().setOnMouseClicked(event -> {
                            FXMLLoader loader = new FXMLLoader();
                            try {
                                AnchorPane pane = loader.load(getClass().getResource("add_part.fxml").openStream());
                                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                Stage stage = new Stage();
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
}
