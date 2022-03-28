package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.admin.models.Get_user;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Users extends Application implements Initializable {
    @FXML
    private TableColumn<Get_user, String> action_col;

    @FXML
    private TableColumn<Get_user, Timestamp> date_col;

    @FXML
    private TableColumn<Get_user, Integer> id_col;

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
    private TableView<Get_user> table;

    ObservableList<Get_user> list = FXCollections.observableArrayList();

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

    public void load() {
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from login_info");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImageView imgView = img(rs);
                list.add(new Get_user(rs.getInt(1), rs.getString(2), imgView, rs.getTimestamp(5), rs.getString(6), rs.getString(3)));

            }
            table.setItems(list);

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initialize(URL url, ResourceBundle rb) {

        id_col.setCellValueFactory(new PropertyValueFactory<Get_user, Integer>("id"));
        mat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("matricule"));
        photo_col.setCellValueFactory(new PropertyValueFactory<Get_user, ImageView>("photo"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("nom"));
        stat_col.setCellValueFactory(new PropertyValueFactory<Get_user, String>("user"));


        load();


    }


    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("add_user.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            main.setEffect(new BoxBlur(5.0, 5.0, 1));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    main.setEffect(new BoxBlur(0, 0, 0));
                }
            });


        } catch (IOException ex) {
        }


    }


    @Override
    public void start(Stage stage) throws Exception {


    }
}
