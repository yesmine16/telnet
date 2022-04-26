package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Bom;
import com.java.telnet.admin.models.Get_history;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class History implements Initializable {

    @FXML
    private ToggleGroup grp;
    @FXML
    private TextField recherche;
    @FXML
    private BorderPane content;

    TableView<Get_history> table = new TableView<Get_history>();
    TableView<Bom> table2 = new TableView<Bom>();

    ObservableList<Get_history> list = FXCollections.observableArrayList();
    ObservableList<Bom> list2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Get_history, String> part = new TableColumn<Get_history, String>("Composant id");
        TableColumn<Get_history, String> resp = new TableColumn<Get_history, String>("Crée par");
        TableColumn<Get_history, String> event = new TableColumn<Get_history, String>("Événement");
        TableColumn<Get_history, String> date = new TableColumn<Get_history, String>("Date création");
        TableColumn<Get_history, Integer> id = new TableColumn<Get_history, Integer>("ID");
        TableColumn<Bom, String> date1 = new TableColumn<Bom, String>("Date ajout");

        TableColumn<Bom, String> added_by = new TableColumn<Bom, String>("Ajouté par");
        TableColumn<Bom, String> projet = new TableColumn<Bom, String>("Projet");
        TableColumn<Bom, String> comp = new TableColumn<Bom, String>("Composant utilisé");
        TableColumn<Bom, Integer> qty = new TableColumn<Bom, Integer>("Quantité");


        table.getColumns().addAll(date,id, part, resp, event);
        table2.getColumns().addAll(date1,comp, projet, qty, added_by);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        id.setCellValueFactory(new PropertyValueFactory<Get_history, Integer>("id"));
        part.setCellValueFactory(new PropertyValueFactory<Get_history, String>("part_id"));
        resp.setCellValueFactory(new PropertyValueFactory<Get_history, String>("resp"));
        event.setCellValueFactory(new PropertyValueFactory<Get_history, String>("event"));
        date.setCellValueFactory(new PropertyValueFactory<Get_history, String>("date"));
        date1.setCellValueFactory(new PropertyValueFactory<Bom, String>("date"));

        added_by.setCellValueFactory(new PropertyValueFactory<Bom, String>("resp"));
        qty.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("qty"));
        projet.setCellValueFactory(new PropertyValueFactory<Bom, String>("projet"));
        comp.setCellValueFactory(new PropertyValueFactory<Bom, String>("nom"));
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from history");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Get_history(null, rs.getString(3), rs.getTimestamp(2).toString(), rs.getString(4), rs.getInt(1)));
                table.setItems(list);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from bom");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list2.add(new Bom(rs.getTimestamp(8).toString(),rs.getString(7), rs.getString(10), rs.getString(6), rs.getInt(4)));
                table2.setItems(list2);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        filter();
        radio();
        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {


                radio();
            }

        });

    }

    public void filter() {
        FilteredList<Get_history> filteredData = new FilteredList<>(list, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hist -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (hist.getId() != -1) {
                    return true;
                } else if (hist.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (hist.getResp().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (hist.getPart_id().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (hist.getEvent().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<Get_history> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void radio() {
        if (grp.getToggles().get(0).isSelected()) {
            content.setCenter(null);
            content.setCenter(table);
        } else {
            content.setCenter(null);
            content.setCenter(table2);


        }
    }

}
