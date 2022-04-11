package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Bom;
import com.java.telnet.admin.models.Get_achat;
import com.java.telnet.admin.models.Get_project;
import com.java.telnet.admin.models.Get_user;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Projet implements Initializable {


    @FXML
    private TableColumn<Get_project, String> nom;
    @FXML
    private TableColumn<Get_project, String> comment;

    @FXML
    private TableColumn<Get_project, String> created_by;

    @FXML
    private TableColumn<Get_project, String> date;

    @FXML
    private TableColumn<Get_project, String> desc;
    @FXML
    private TableColumn<Get_project, String> tarif;

    @FXML
    private TableColumn<Get_project, String> team;
    @FXML
    private TableColumn<Get_project, String> etat;

    @FXML
    private FontAwesomeIconView eye;

    @FXML
    private Pane pane;

    @FXML
    private Label nom_projet;

    @FXML
    private TabPane tab_pane;

    @FXML
    private TableView<Get_project> table;
    @FXML
    private HBox titre;
    @FXML
    private TableColumn<Bom, String> comm;
    @FXML
    private TableColumn<Bom, String> date_creation;
    @FXML
    private TableColumn<Bom, String> description;
    @FXML
    private TableColumn<Bom, String> design;
    @FXML
    private TableColumn<Bom, String> num, resp, label, name,etat_bom;
    @FXML
    private TableColumn<Bom, Integer> in_stock, qty, to_buy;
    @FXML
    private TableView<Bom> table2;
    @FXML
    private TextField quantite;
    @FXML
    private TextArea descr;
    @FXML
    private Label add;
    @FXML
    private TextField recherche;
    static String pr;
    ObservableList<Get_project> list = FXCollections.observableArrayList();
    ObservableList<Bom> list2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eye.setVisible(true);
        titre.setVisible(false);
        tab_pane.setVisible(false);
        nom.setCellValueFactory(new PropertyValueFactory<Get_project, String>("nom"));
        etat.setCellValueFactory(new PropertyValueFactory<Get_project, String>("etat"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_project, String>("desc"));
        created_by.setCellValueFactory(new PropertyValueFactory<Get_project, String>("created_by"));
        date.setCellValueFactory(new PropertyValueFactory<Get_project, String>("date"));
        tarif.setCellValueFactory(new PropertyValueFactory<Get_project, String>("tarif"));
        team.setCellValueFactory(new PropertyValueFactory<Get_project, String>("team"));
        comment.setCellValueFactory(new PropertyValueFactory<Get_project, String>("comment"));
        resp.setCellValueFactory(new PropertyValueFactory<Bom, String>("resp"));
        num.setCellValueFactory(new PropertyValueFactory<Bom, String>("num"));
        label.setCellValueFactory(new PropertyValueFactory<Bom, String>("label"));
        description.setCellValueFactory(new PropertyValueFactory<Bom, String>("desc"));
        design.setCellValueFactory(new PropertyValueFactory<Bom, String>("design"));
        comm.setCellValueFactory(new PropertyValueFactory<Bom, String>("comment"));
        date_creation.setCellValueFactory(new PropertyValueFactory<Bom, String>("date"));
        qty.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("qty"));
        in_stock.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("stock"));
        to_buy.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("to_buy"));
        name.setCellValueFactory(new PropertyValueFactory<Bom, String>("nom"));
        etat_bom.setCellValueFactory(new PropertyValueFactory<Bom, String>("etat"));


        try {
            projet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setOnMouseClicked(eventHandler -> {
                    eye.setVisible(false);
                    tab_pane.setVisible(true);
                    titre.setVisible(true);

                    for (Get_project list : table.getSelectionModel().getSelectedItems()) {
                        for (int i = 1; i <= 1; i++) {
                            list2.clear();
                            nom_projet.setText(list.getNom());
                            pr=list.getId();
                            DB db = new DB();
                            try {
                                PreparedStatement ps = db.connect().prepareStatement("select * from bom where projet=?");
                                ps.setString(1, list.getId());
                                ResultSet rs = ps.executeQuery();
                                PreparedStatement ps1 = db.connect().prepareStatement("select stat from ressources where internal_pn=?");


                                while (rs.next()) {
                                    ps1.setString(1, rs.getString(2));
                                    ResultSet rs1 = ps1.executeQuery();
                                    if (rs1.next())
                                        list2.add(new Bom(rs.getString(1), rs.getString(12), rs.getString(11), null, rs.getString(3), rs.getString(5), rs.getString(9), rs.getTimestamp(10).toString(), rs.getInt(4), rs.getInt(7), rs.getInt(8),rs1.getString(1)));
                                }
                                table2.setItems(list2);
                                filter();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );
        add.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader();
            try {
                AnchorPane pane = loader.load(getClass().getResource("create_bom.fxml").openStream());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                stage.setScene(new Scene(pane));
                stage.show();


            } catch (IOException e) {
                System.out.println(e.toString());
            }
        });

    }

    public void filter() {
        FilteredList<Bom> filteredData = new FilteredList<>(list2, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bom -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (bom.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (bom.getLabel().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(bom.getNum()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<Bom> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table2.comparatorProperty());
        table2.setItems(sortedData);
    }

    public void projet() throws SQLException {
        DB db = new DB();
        PreparedStatement ps = db.connect().prepareStatement("select * from projet");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            list.add(new Get_project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6).toString(), rs.getString(7), rs.getString(8), rs.getString(9)));
            table.setItems(list);
        }
    }


}
