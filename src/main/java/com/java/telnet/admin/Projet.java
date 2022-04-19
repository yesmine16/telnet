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
import javafx.scene.image.ImageView;
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
import java.util.ArrayList;
import java.util.List;
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
    private ScrollPane tab;

    @FXML
    private Label nom_projet, delete, update, delete2, update2;

    @FXML
    private TabPane tab_pane;

    @FXML
    private TableView<Get_project> table;
    @FXML
    private HBox titre;
    @FXML
    FontAwesomeIconView slide;

    @FXML
    private TableColumn<Bom, String> date_creation;
    @FXML
    private TableColumn<Bom, String> description;
    @FXML
    private TableColumn<Bom, String> design;
    @FXML
    private TableColumn<Bom, String> num, resp, label, name, etat_bom,cat;
    @FXML
    private TableColumn<Bom, Integer> qty;
    @FXML
    private TableView<Bom> table2;
    @FXML
    private TableColumn<Prod, String> add_by;

    @FXML
    private TableColumn<Prod, String> avan;
    @FXML
    private TableColumn<Prod, String> id_prod;
    @FXML
    private TableColumn<Prod, String> param;

    @FXML
    private TableColumn<Prod, String> projet_prod;
    @FXML
    private TableColumn<Prod, String> qty_prod;
    @FXML
    private TableColumn<Prod, String> soft;
    @FXML
    private TableColumn<Prod, HBox> action;
    @FXML
    private TableColumn<Prod, String> start;
    @FXML
    private TableView<Prod> table3;

    @FXML
    private TableColumn<Prod, String> update_date,test;

    @FXML
    private Label add;
    @FXML
    private TextField recherche;
    static String pr;
    ObservableList<Get_project> list = FXCollections.observableArrayList();
    ObservableList<Bom> list2 = FXCollections.observableArrayList();
    static List<Get_project> list3 = new ArrayList<Get_project>();
    static List<Bom> list4 = new ArrayList<Bom>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eye.setVisible(true);
        titre.setVisible(false);
        tab_pane.setVisible(false);
        nom.setCellValueFactory(new PropertyValueFactory<Get_project, String>("nom"));
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
        date_creation.setCellValueFactory(new PropertyValueFactory<Bom, String>("date"));
        qty.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("qty"));
        name.setCellValueFactory(new PropertyValueFactory<Bom, String>("nom"));
        etat_bom.setCellValueFactory(new PropertyValueFactory<Bom, String>("etat"));
        cat.setCellValueFactory(new PropertyValueFactory<Bom, String>("cat"));



        try {
            projet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setOnMouseClicked(eventHandler -> {

                    for (Get_project list : table.getSelectionModel().getSelectedItems()) {
                        for (int i = 1; i <= 1; i++) {

                            eye.setVisible(false);
                            tab_pane.setVisible(true);
                            titre.setVisible(true);
                            list2.clear();
                            nom_projet.setText(list.getNom());
                            pr = list.getId();
                            DB db = new DB();
                            try {
                                PreparedStatement ps = db.connect().prepareStatement("select * from bom where projet=?");
                                ps.setString(1, list.getId());
                                ResultSet rs = ps.executeQuery();
                                PreparedStatement ps1 = db.connect().prepareStatement("select stat from ressources where internal_pn=?");
                                PreparedStatement ps2 = db.connect().prepareStatement("select count(*) from bom where name=?");

                                while (rs.next()) {
                                    ps1.setString(1, rs.getString(2));
                                    ResultSet rs1 = ps1.executeQuery();
                                    ps2.setString(1, rs.getString(10));
                                    ResultSet rs2 = ps2.executeQuery();

                                    if (rs1.next() && rs2.next())
                                        list2.add(new Bom(rs.getInt(1), rs.getString(2), rs.getString(10), rs.getString(9), rs.getString(3), rs.getString(5), rs.getString(7), rs.getTimestamp(8).toString(), rs2.getInt(1), rs1.getString(1), rs.getString(6),rs.getString(11)));
                                }
                                table2.setItems(list2);
                                filter();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            delete.setOnMouseClicked(e -> {
                                try {
                                    PreparedStatement ps = db.connect().prepareStatement("delete from bom where projet=?;delete from projet where id=? ");
                                    ps.setString(1, list.getId());
                                    ps.setString(2, list.getId());
                                    ps.executeUpdate();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Projet " + list.getNom() + " a été supprimé avec succès");
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }

                            });
                            update.setOnMouseClicked(e -> {
                                update(list3, list.getId(), list.getNom(), list.getDesc(), list.getTarif(), list.getDate(), list.getCreated_by(), list.getTeam(), list.getComment());
                                FXMLLoader loader = new FXMLLoader();
                                try {
                                    AnchorPane pane = loader.load(getClass().getResource("add_project.fxml").openStream());
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                                    stage.setScene(new Scene(pane));
                                    stage.show();


                                } catch (IOException exception) {
                                }
                            });


                        }
                    }
                }
        );
        table2.setOnMouseClicked(e -> {
            for (Bom list : table2.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    DB db = new DB();
                    update2.setOnMouseClicked(ev -> {
                        update2(list4, list.getId(), list.getNum(), list.getNom(), list.getLabel(), list.getDesign(), list.getComment(), list.getResp(), list.getDate(), list.getQty(), list.getEtat(), list.getProjet(),list.getCat());
                        FXMLLoader loader = new FXMLLoader();
                        try {
                            AnchorPane pane = loader.load(getClass().getResource("create_bom.fxml").openStream());
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                            stage.setScene(new Scene(pane));
                            stage.show();


                        } catch (IOException exception) {
                        }
                    });
                    delete2.setOnMouseClicked(ev -> {
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("delete from bom where id=? ");
                            ps.setInt(1, list.getId());
                            ps.executeUpdate();
                            ps.close();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Le composant "+ list.getNom()+" a été supprimé avec succès");
                            alert.show();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    });
                }
            }
        });
        add.setOnMouseClicked(event -> {
            list4.clear();
            FXMLLoader loader = new FXMLLoader();
            try {
                AnchorPane pane = loader.load(getClass().getResource("create_bom.fxml").openStream());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                stage.setScene(new Scene(pane));
                stage.show();


            } catch (IOException e) {
            }
        });
        slide.setOnMouseClicked(event -> {
            if (tab.isVisible()) {
                tab.setVisible(false);
                tab.setManaged(false);
            } else {
                tab.setVisible(true);
                tab.setManaged(true);

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
        list.clear();
        PreparedStatement ps = db.connect().prepareStatement("select * from projet");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Get_project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5).toString(), rs.getString(6), rs.getString(7), rs.getString(8)));
            table.setItems(list);
        }
    }

    public void add_project() throws IOException {
        list3.clear();
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane pane = loader.load(getClass().getResource("add_project.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
        }
    }

    public static void update(List<Get_project> list, String id, String nom, String desc, String tarif, String date, String resp, String team, String comm) {
        list.clear();
        list.add(new Get_project(id, nom, desc, tarif, date, resp, team, comm));

    }

    public static void update2(List<Bom> list, Integer id, String num, String nom, String label, String design, String comment, String resp, String date, Integer qty, String stat, String pr,String cat) {
        list.clear();
        list.add(new Bom(id, num, nom, label, design, comment, resp, date, qty, stat, pr,cat));

    }
}
