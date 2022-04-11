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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private StackPane content;
    @FXML
    private TextField recherche;

    ObservableList<Get_user> list = FXCollections.observableArrayList();
     static List<Get_user> list2 = new ArrayList<Get_user>() {};


    public ImageView img(ResultSet rs) throws Exception {
        ImageView imgView = new ImageView();
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        imgView.setPreserveRatio(true);
        InputStream is = rs.getBinaryStream("photo");
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
        FontAwesomeIconView edit, delete;

        edit = new FontAwesomeIconView();
        delete = new FontAwesomeIconView();
        HBox hbox = new HBox();
        edit.setGlyphName("PENCIL");
        delete.setGlyphName("TRASH");
        edit.setSize("1.5em");
        edit.setCursor(Cursor.HAND);
        delete.setCursor(Cursor.HAND);
        delete.setSize("1.5em");
        edit.setFill(Color.web("#435B7B"));
        delete.setFill(Color.web("#435B7B"));
        hbox.getChildren().add(edit);
        hbox.getChildren().add(delete);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    public void load() {
        DB db = new DB();
        try {
            list.clear();
            PreparedStatement ps = db.connect().prepareStatement("select * from login_info");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImageView imgView = img(rs);
                HBox hbox = action();
                list.add(new Get_user(rs.getInt(1), rs.getString(2), imgView, rs.getTimestamp(5).toString(), rs.getString(6), rs.getString(3), hbox, rs.getString(7), rs.getString(8)));
            }
            table.setItems(list);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("add_user.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);

    }

    public static List<Get_user> getList() {
        return list2;
    }

    public void initialize(URL url, ResourceBundle rb) {


        mat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("matricule"));
        photo_col.setCellValueFactory(new PropertyValueFactory<Get_user, ImageView>("photo"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("nom"));
        phone_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("phone"));
        email_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("email"));
        stat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("user"));
        date_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("date"));
        action_col.setCellValueFactory(new PropertyValueFactory<Get_user, HBox>("action"));
        load();
        filter();
        table.setOnMouseClicked(eventHandler -> {
            for (Get_user list : table.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    list.getAction().getChildren().get(0).setOnMouseClicked(event -> {
                        try {
                            list2.clear();
                            add();
                            list2.add(new Get_user(list.getId(), list.getMatricule(), list.getPhoto(), null, list.getNom(), list.getUser(), null, list.getPhone(), list.getEmail()));

                        } catch (IOException ex) {
                        }
                    });

                    list.getAction().getChildren().get(1).setOnMouseClicked(event -> {

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
                }
            }
        });
    }

public void filter(){
    FilteredList<Get_user> filteredData = new FilteredList<>(list, b -> true);
    recherche.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(users -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if (users.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                return true;
            } else if (users.getUser().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            }
            else if (String.valueOf(users.getMatricule()).indexOf(lowerCaseFilter)!=-1)
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


}

