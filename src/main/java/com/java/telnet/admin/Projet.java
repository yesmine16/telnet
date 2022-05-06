package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Bom;
import com.java.telnet.admin.models.Get_project;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Projet implements Initializable {


    @FXML
    private TableColumn<Get_project, String> nom;


    @FXML
    private TableColumn<Get_project, String> created_by;

    @FXML
    private TableColumn<Get_project, String> date;

    @FXML
    private TableColumn<Get_project, String> desc;


    @FXML
    private TableColumn<Get_project, String> team;


    @FXML
    private FontAwesomeIconView eye;

    @FXML
    private AnchorPane tab;

    @FXML
    private Label nom_projet, delete, update, delete2, update2, test, redo, redo_pr;
    @FXML
    private VBox content;
    @FXML
    private TabPane tab_pane;

    @FXML
    private TableView<Get_project> table;
    @FXML
    private HBox titre;
    @FXML
    private FontAwesomeIconView slide;
    @FXML
    private TableColumn<Bom, String> date_creation;
    @FXML
    private TableColumn<Bom, String> description;
    @FXML
    private TableColumn<Bom, String> design;
    @FXML
    private TableColumn<Bom, String> num, resp, label, name;
    @FXML
    private TableColumn<Bom, Integer> qty;
    @FXML
    private TableView<Bom> table2;
    @FXML
    private TextField test_name;
    @FXML
    private ChoiceBox<String> test_bom;
    @FXML
    private ToggleGroup grp;
    @FXML
    private HBox crud_bom;

    @FXML
    private HBox crud_pr;

    @FXML
    private AnchorPane crud_test;
    @FXML
    private ContextMenu menu_bom;

    @FXML
    private ContextMenu menu_pr;

    @FXML
    private Label add;
    @FXML
    private TextField recherche;
    static Integer pr;
    ObservableList<Get_project> list = FXCollections.observableArrayList();
    ObservableList<Bom> list2 = FXCollections.observableArrayList();
    static List<Get_project> list3 = new ArrayList<>();
    static List<Bom> list4 = new ArrayList<>();
    MenuItem menuItem3 = new MenuItem("Actualiser");
    MenuItem m3 = new MenuItem("Actualiser");
    MenuItem menuItem1 = new MenuItem("Modifier");
    MenuItem menuItem2 = new MenuItem("Supprimer");
    MenuItem m1 = new MenuItem("Modifier");
    MenuItem m2 = new MenuItem("Supprimer");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crud_pr.setManaged(false);
        crud_bom.setManaged(false);
        crud_test.setManaged(false);
        menu_pr.getItems().clear();        menu_bom.getItems().clear();

        menu_pr.getItems().add(menuItem3);
        menu_bom.getItems().add(m3);
        crud_pr.setVisible(false);
        crud_bom.setVisible(false);
        crud_test.setVisible(false);

        String[] s = LoginController.getS();
        List ls = List.of(s[3].replaceAll("[{}]", "").split(","));

        eye.setVisible(true);
        titre.setVisible(false);
        tab_pane.setVisible(false);
        nom.setCellValueFactory(new PropertyValueFactory<Get_project, String>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_project, String>("desc"));
        created_by.setCellValueFactory(new PropertyValueFactory<Get_project, String>("created_by"));
        date.setCellValueFactory(new PropertyValueFactory<Get_project, String>("date"));
        team.setCellValueFactory(new PropertyValueFactory<Get_project, String>("team"));
        resp.setCellValueFactory(new PropertyValueFactory<Bom, String>("resp"));
        num.setCellValueFactory(new PropertyValueFactory<Bom, String>("num"));
        label.setCellValueFactory(new PropertyValueFactory<Bom, String>("label"));
        description.setCellValueFactory(new PropertyValueFactory<Bom, String>("desc"));
        design.setCellValueFactory(new PropertyValueFactory<Bom, String>("design"));
        date_creation.setCellValueFactory(new PropertyValueFactory<Bom, String>("date"));
        qty.setCellValueFactory(new PropertyValueFactory<Bom, Integer>("qty"));
        name.setCellValueFactory(new PropertyValueFactory<Bom, String>("nom"));

        menuItem3.setOnAction(ev -> {
            try {
                projet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        try {
            projet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setOnMouseClicked(eventHandler -> {

                    for (Get_project list : table.getSelectionModel().getSelectedItems()) {
                        for (int i = 1; i <= 1; i++) {
                            if (ls.get(1).equals("oui")) {
                                crud_pr.setVisible(true);
                                crud_bom.setVisible(true);
                                crud_test.setVisible(true);
                                crud_pr.setManaged(true);
                                crud_bom.setManaged(true);
                                crud_test.setManaged(true);

                                menu_pr.getItems().add(menuItem1);
                                menu_pr.getItems().add(menuItem2);

                                menu_bom.getItems().addAll(m1);
                                menu_bom.getItems().addAll(m2);
                            }
                            content.getChildren().clear();
                            test_bom.getItems().clear();
                            eye.setVisible(false);
                            tab_pane.setVisible(true);
                            titre.setVisible(true);
                            list2.clear();
                            nom_projet.setText(list.getNom());
                            pr = list.getId();
                            DB db = new DB();
                            m3.setOnAction(ev -> {
                                try {
                                    list2.clear();
                                    PreparedStatement ps = db.connect().prepareStatement("select * from bom where projet=?");
                                    ps.setInt(1, list.getId());
                                    ResultSet rs = ps.executeQuery();
                                    PreparedStatement ps2 = db.connect().prepareStatement("select count(*) from bom where name=?");

                                    while (rs.next()) {
                                        ps2.setString(1, rs.getString(10));
                                        ResultSet rs2 = ps2.executeQuery();

                                        if (rs2.next())
                                            list2.add(new Bom(rs.getInt(1), rs.getString(2), rs.getString(9), rs.getString(8), rs.getString(3), rs.getString(5), rs.getString(6), rs.getTimestamp(7).toString(), rs2.getInt(1), rs.getInt(10)));
                                    }
                                    table2.setItems(list2);
                                    filter();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                            try {
                                PreparedStatement ps = db.connect().prepareStatement("select * from bom where projet=?");
                                ps.setInt(1, list.getId());
                                ResultSet rs = ps.executeQuery();
                                PreparedStatement ps2 = db.connect().prepareStatement("select count(*) from bom where name=?");

                                while (rs.next()) {
                                    ps2.setString(1, rs.getString(10));
                                    ResultSet rs2 = ps2.executeQuery();

                                    if (rs2.next())
                                        list2.add(new Bom(rs.getInt(1), rs.getString(2), rs.getString(9), rs.getString(8), rs.getString(3), rs.getString(5), rs.getString(6), rs.getTimestamp(7).toString(), rs2.getInt(1), rs.getInt(10)));
                                }
                                table2.setItems(list2);
                                filter();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            delete.setOnMouseClicked(e -> {
                                try {
                                    PreparedStatement ps = db.connect().prepareStatement("delete from bom where projet=?;delete from projet where id=? ");
                                    ps.setInt(1, list.getId());
                                    ps.setInt(2, list.getId());
                                    ps.executeUpdate();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Projet " + list.getNom() + " a été supprimé avec succès");
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }

                            });
                            menuItem2.setOnAction(e -> {
                                try {
                                    PreparedStatement ps = db.connect().prepareStatement("delete from bom where projet=?;delete from projet where id=? ");
                                    ps.setInt(1, list.getId());
                                    ps.setInt(2, list.getId());
                                    ps.executeUpdate();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Projet " + list.getNom() + " a été supprimé avec succès");
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }

                            });
                            update.setOnMouseClicked(e -> {
                                update(list3, list.getId(), list.getNom(), list.getDesc(), list.getDate(), list.getCreated_by(), list.getTeam());
                                FXMLLoader loader = new FXMLLoader();
                                try {
                                    AnchorPane pane = loader.load(getClass().getResource("add_project.fxml").openStream());
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                                    stage.setScene(new Scene(pane));
                                    stage.show();


                                } catch (IOException ignored) {
                                }
                            });
                            menuItem1.setOnAction(ev -> {
                                update(list3, list.getId(), list.getNom(), list.getDesc(), list.getDate(), list.getCreated_by(), list.getTeam());
                                FXMLLoader loader = new FXMLLoader();
                                try {
                                    AnchorPane pane = loader.load(getClass().getResource("add_project.fxml").openStream());
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                                    stage.setScene(new Scene(pane));
                                    stage.show();


                                } catch (IOException ignored) {
                                }
                            });
                            try {
                                PreparedStatement ps = db.connect().prepareStatement("select id,name from bom where projet=?");
                                ps.setInt(1, list.getId());
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                    int x = rs.getInt(1);

                                    test_bom.getItems().add(x + "  " + rs.getString(2));
                                }

                                ps.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            redo.setOnMouseClicked(ev -> {
                                try {
                                    list2.clear();
                                    PreparedStatement ps = db.connect().prepareStatement("select * from bom where projet=?");
                                    ps.setInt(1, list.getId());
                                    ResultSet rs = ps.executeQuery();
                                    PreparedStatement ps2 = db.connect().prepareStatement("select count(*) from bom where name=?");

                                    while (rs.next()) {
                                        ps2.setString(1, rs.getString(10));
                                        ResultSet rs2 = ps2.executeQuery();

                                        if (rs2.next())
                                            list2.add(new Bom(rs.getInt(1), rs.getString(2), rs.getString(9), rs.getString(8), rs.getString(3), rs.getString(5), rs.getString(6), rs.getTimestamp(7).toString(), rs2.getInt(1), rs.getInt(10)));
                                    }
                                    table2.setItems(list2);
                                    filter();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                            try {

                                PreparedStatement ps = db.connect().prepareStatement("SELECT b.name, t.test, t.valide FROM test t,bom b where t.bom_id=b.id and t.project_id=? order by t.id desc");
                                ps.setInt(1, list.getId());
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                    load_tests(rs.getString(1), rs.getString(2), rs.getBoolean(3));
                                }

                                ps.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        test.setOnMouseClicked(ev -> {
                            test(list.getId());
                        });
                    }
                }
        );
        redo_pr.setOnMouseClicked(ev -> {
            try {
                projet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        table2.setOnMouseClicked(e -> {
            for (Bom list : table2.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    DB db = new DB();
                    update2.setOnMouseClicked(ev -> {
                        update2(list4, list.getId(), list.getNum(), list.getNom(), list.getLabel(), list.getDesign(), list.getComment(), list.getResp(), list.getDate(), list.getQty(), list.getProjet());
                        FXMLLoader loader = new FXMLLoader();
                        try {
                            AnchorPane pane = loader.load(getClass().getResource("create_bom.fxml").openStream());
                            Stage stage = new Stage();
                            stage.setTitle("Ajouter nomenclature");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                            stage.setScene(new Scene(pane));
                            stage.show();


                        } catch (IOException exception) {
                        }
                    });
                    m1.setOnAction(ev -> {
                        update2(list4, list.getId(), list.getNum(), list.getNom(), list.getLabel(), list.getDesign(), list.getComment(), list.getResp(), list.getDate(), list.getQty(), list.getProjet());
                        FXMLLoader loader = new FXMLLoader();
                        try {
                            AnchorPane pane = loader.load(getClass().getResource("create_bom.fxml").openStream());
                            Stage stage = new Stage();
                            stage.setTitle("Ajouter nomenclature");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                            stage.setScene(new Scene(pane));
                            stage.show();


                        } catch (IOException exception) {
                        }
                    });
                    delete2.setOnMouseClicked(ev -> {
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("delete from bom where id=?");
                            ps.setInt(1, list.getId());
                            ps.executeUpdate();
                            ps.close();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Le composant " + list.getNom() + " a été supprimé avec succès");
                            alert.show();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    });
                    m2.setOnAction(ev -> {
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("delete from test where bom_id=?;delete from bom where id=?");
                            ps.setInt(1, list.getId());
                            ps.setInt(2, list.getId());
                            ps.executeUpdate();
                            ps.close();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Le composant " + list.getNom() + " a été supprimé avec succès");
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
                stage.setTitle("Ajouter une nomenclature");
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

    Boolean v = true;

    public void test(Integer id) {
        if (test_name.getText().isEmpty()) {
            v = false;

        }

        if (grp.getSelectedToggle() == null) {
            v = false;

        }
        if (test_bom.getSelectionModel().isEmpty()) {
            v = false;
        }

        if (v) {
            try {
                DB db = new DB();
                PreparedStatement ps = db.connect().prepareStatement("INSERT INTO public.test(bom_id, test, valide,project_id) VALUES (?, ?, ?,?)");
                ps.setInt(1, Integer.parseInt(test_bom.getValue().split("  ")[0]));
                ps.setString(2, test_name.getText());
                ps.setInt(4, id);
                if (grp.getToggles().get(0).isSelected())
                    ps.setBoolean(3, true);
                else ps.setBoolean(3, false);


                if (ps.executeUpdate() > 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    DialogPane dialogPane = alert.getDialogPane();
                    alert.setTitle("confirmer votre choix");
                    alert.setContentText("Ajouter le test " + test_name.getText() + " au " + test_bom.getValue().split("  ")[1] + "?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setContentText("Test ajouté avec succés");
                        alert1.show();
                        test_bom.getSelectionModel().clearSelection();
                        test_name.clear();
                        grp.getSelectedToggle().setSelected(false);
                        content.getChildren().clear();
                        try {
                            PreparedStatement ps1 = db.connect().prepareStatement("SELECT b.name, t.test, t.valide FROM test t,bom b where t.bom_id=b.id and t.project_id=? order by t.id desc");
                            ps1.setInt(1, pr);
                            ResultSet rs = ps1.executeQuery();
                            while (rs.next()) {
                                load_tests(rs.getString(1), rs.getString(2), rs.getBoolean(3));
                            }
                            ps.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                ps.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            list.add(new Get_project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).toString(), rs.getString(5), rs.getString(6).replace("[", "").replace("]", "")));
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
            stage.setTitle("Nouveau projet");
            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
        }
    }

    public static void update(List<Get_project> list, Integer id, String nom, String desc, String date, String resp, String team) {
        list.clear();
        list.add(new Get_project(id, nom, desc, date, resp, team));

    }

    public static void update2(List<Bom> list, Integer id, String num, String nom, String label, String design, String comment, String resp, String date, Integer qty, Integer pr) {
        list.clear();
        list.add(new Bom(id, num, nom, label, design, comment, resp, date, qty, pr));

    }

    public void load_tests(String bom, String test, Boolean valide) {

        AnchorPane hbox = new AnchorPane();
        hbox.getStyleClass().add("test");
        hbox.setPrefHeight(50.0);
        Label lb = new Label();
        lb.setFont(new Font("Lucida Console", 15));
        Label lb1 = new Label();
        lb1.setFont(new Font("Lucida Console", 15));

        Label lb2 = new Label();
        lb2.setFont(new Font("Lucida Console", 15));

        lb.setText(bom);
        lb1.setText(test);
        if (valide)
            lb2.setText("Valide");
        else lb2.setText("Non valide");
        lb.setLayoutY(15.0);
        lb1.setLayoutY(15.0);
        lb2.setLayoutY(15.0);
        lb.setLayoutX(10.0);
        lb2.setLayoutX(700.0);
        lb1.setLayoutX(440.0);


        hbox.getChildren().addAll(lb, lb1, lb2);
        content.getChildren().add(hbox);


    }


}
