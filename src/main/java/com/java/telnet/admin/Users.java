package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Get_user;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PopOver;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Users extends Application implements Initializable {
    @FXML
    private TableColumn<Get_user, HBox> action_col;

    @FXML
    private TableColumn<Get_user, String> date_col;


    @FXML
    private AnchorPane main;

    @FXML
    private TableColumn<Get_user, String> mat_col;

    @FXML
    private TableColumn<Get_user, String> nom_col;

    @FXML
    private TableColumn<Get_user, ImageView> photo_col;

    @FXML
    private TableColumn<Get_user, String> stat_col;
    @FXML
    private TableColumn<Get_user, String> email_col, phone_col;

    @FXML
    private TableView<Get_user> table;


    @FXML
    private  StackPane content;
    @FXML
    private TextField recherche;

    ObservableList<Get_user> list = FXCollections.observableArrayList();
    static List<Get_user> list2 = new ArrayList<Get_user>();


    public ImageView img(ResultSet rs, String name) throws Exception {
        ImageView imgView = new ImageView();
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        imgView.setPreserveRatio(true);
        InputStream is = rs.getBinaryStream(name);
        OutputStream os = new FileOutputStream(new File("photo.jpg"));
        byte[] content = new byte[2048];
        int size = 0;
        while ((size = is.read(content)) != -1) {
            os.write(content, 0, size);
        }
        os.close();
        is.close();
        Image imagex = new Image("file:photo.jpg");
        imgView.setImage(imagex);
        return imgView;
    }

    public void popover() {
        table.setOnMouseClicked(eventHandler -> {
            for (Get_user list : table.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    PopOver popup = new PopOver();
                    HBox hbox = new HBox();
                    hbox.prefHeight(100.0);
                    hbox.prefWidth(100.0);
                    hbox.getChildren().add(list.getPhoto());
                    TextField txt = new TextField(list.getNom());
                    hbox.getChildren().add(txt);
                    popup.setContentNode(hbox);
                    popup.show(table);
                }
            }
        });
    }


    public HBox action() {
        FontAwesomeIconView edit, delete, qr;

        edit = new FontAwesomeIconView();
        delete = new FontAwesomeIconView();
        qr = new FontAwesomeIconView();

        HBox hbox = new HBox();
        edit.setGlyphName("PENCIL");
        qr.setGlyphName("QRCODE");
        delete.setGlyphName("TRASH");
        edit.setSize("1.5em");
        qr.setSize("1.5em");
        delete.setSize("1.5em");
        edit.setCursor(Cursor.HAND);
        delete.setCursor(Cursor.HAND);
        qr.setCursor(Cursor.HAND);
        edit.setFill(Color.web("#435B7B"));
        qr.setFill(Color.web("#435B7B"));
        delete.setFill(Color.web("#435B7B"));
        hbox.getChildren().add(qr);
        hbox.getChildren().add(edit);
        hbox.getChildren().add(delete);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    public void load() throws SQLException {
        DB db = new DB();
        try {
            list.clear();
            PreparedStatement ps = db.connect().prepareStatement("select * from login_info");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImageView imgView = img(rs, "photo");
                ImageView imgView2 = img(rs, "qr");

                HBox hbox = action();
                list.add(new Get_user(rs.getInt(1), rs.getString(2), imgView, rs.getTimestamp(5).toString(), rs.getString(6), rs.getString(3), hbox, rs.getString(7), rs.getString(8), imgView2));
            }
            table.setItems(list);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane pane = loader.load(getClass().getResource("add_user.fxml").openStream());
            Stage stage = new Stage();
            stage.setHeight(800);
            stage.initModality(Modality.APPLICATION_MODAL);
            pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
        }
    }

    static String[] s = new String[7];


    public void initialize(URL url, ResourceBundle rb) {
        mat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("matricule"));
        photo_col.setCellValueFactory(new PropertyValueFactory<Get_user, ImageView>("photo"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("nom"));
        phone_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("phone"));
        email_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("email"));
        stat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("user"));
        date_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("date"));
        action_col.setCellValueFactory(new PropertyValueFactory<Get_user, HBox>("action"));
        try {
            load();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        filter();
        list2.clear();
        table.setOnMouseClicked(eventHandler -> {
            for (Get_user list : table.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    list.getAction().getChildren().get(1).setOnMouseClicked(event -> {
                        try {

                            DB db = new DB();
                            PreparedStatement ps = db.connect().prepareStatement("select photo from login_info where matricule=?");
                            ps.setString(1, list.getMatricule());
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                ImageView imgView = img(rs, "photo");
                                ajout(list2, list.getMatricule(), imgView, list.getNom(), list.getPhone(), list.getEmail(), list.getUser(),list.getId());
                            }
                            PreparedStatement pss = db.connect().prepareStatement("SELECT \"table\", users, parts, projects, storage, history, buy FROM privilege WHERE id = ?");
                            pss.setInt(1, list.getId());
                            ResultSet rss = pss.executeQuery();
                            while (rss.next()) {

                                for (int j = 1; j <= 7; j++) {
                                    s[j-1] = rss.getArray(j).toString();
                                }
                            }
                            add();

                        } catch (IOException ex) {
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception p) {
                        }
                    });

                    list.getAction().getChildren().get(2).setOnMouseClicked(event -> {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                        alert.setTitle("confirmer votre choix");
                        alert.setContentText("Vous etes sur de supprimer " + list.getNom() + "?");
                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {

                            try {
                                DB db = new DB();
                                CallableStatement delete = db.connect().prepareCall("call delete_user(?)");
                                delete.setInt(1, list.getId());
                                delete.execute();

                                load();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


                        }

                    });
                    list.getAction().getChildren().get(0).setOnMouseClicked(event -> {
                        DB db = new DB();
                        PopOver popup = new PopOver();
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("select qr from login_info");
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                ImageView imgView2 = img(rs, "qr");
                                imgView2.prefHeight(100.0);
                                imgView2.prefWidth(100.0);
                                popup.setContentNode(imgView2);
                                popup.show(list.getAction().getChildren().get(0));
                                popup.setAutoHide(true);


                            }


                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    });
                }
            }
        });
    }

    public void filter() {
        FilteredList<Get_user> filteredData = new FilteredList<>(list, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(users -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (users.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (users.getUser().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(users.getMatricule()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<Get_user> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    public static void ajout(List<Get_user> list, String mat, ImageView img, String nom, String phone, String email, String stat,Integer id) {
        list.clear();
        list.add(new Get_user(id, mat, img, null, nom, stat, null, phone, email, null));

    }
}

