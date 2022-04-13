package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Bom;
import com.java.telnet.admin.models.Get_achat;
import com.java.telnet.admin.models.Get_history;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class History implements Initializable {
    @FXML
    private TableColumn<Get_history, String> date;

    @FXML
    private TableColumn<Get_history, String> event;

    @FXML
    private TableColumn<Get_history, Integer> id;

    @FXML
    private TableColumn<Get_history, String> part;

    @FXML
    private TableColumn<Get_history, String> resp;
    @FXML
    private TextField recherche;
    @FXML
    private TableView<Get_history> table;
    ObservableList<Get_history> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Get_history, Integer>("id"));
        part.setCellValueFactory(new PropertyValueFactory<Get_history, String>("part_id"));
        resp.setCellValueFactory(new PropertyValueFactory<Get_history, String>("resp"));
        event.setCellValueFactory(new PropertyValueFactory<Get_history, String>("event"));
        date.setCellValueFactory(new PropertyValueFactory<Get_history, String>("date"));
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from history");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Get_history(rs.getString(2), rs.getString(4), rs.getTimestamp(3).toString(), rs.getString(5), rs.getInt(1)));
                table.setItems(list);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        filter();
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
                    return true;}
                else if (hist.getEvent().indexOf(lowerCaseFilter) != -1)
                    return true;
                    else
                    return false;
            });
        });
        SortedList<Get_history> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}
