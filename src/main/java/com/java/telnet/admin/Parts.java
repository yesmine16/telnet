package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_parts;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Parts implements Initializable {


    @FXML
    private TableColumn<Get_parts, String> classification;

    @FXML
    private TableColumn<Get_parts, String> date_creation;
    @FXML
    private TableColumn<Get_parts, String> desc;
    @FXML
    private Label update, delete, add;
    @FXML
    private TableColumn<Get_parts, String> internal_pn;
    @FXML
    private TableColumn<Get_parts, String> label;
    @FXML
    private TableColumn<Get_parts, String> nom;
    @FXML
    private TableColumn<Get_parts, String> origin;

    @FXML
    private TableColumn<Get_parts, HBox> action;
    @FXML
    private TableColumn<Get_parts, ImageView> img;
    @FXML
    private TableColumn<Get_parts, String> storage;

    @FXML
    private TableView<Get_parts> table;

    @FXML
    private ContextMenu menu;

    @FXML
    private HBox crud;

    ObservableList<Get_parts> list = FXCollections.observableArrayList();
    static List<Get_parts> list2 = new ArrayList<Get_parts>();


    @FXML
    private void add(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main_page.class.getResource("add_part.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
    }

    void load() {
        DB db = new DB();
        try {
            list.clear();
            PreparedStatement ps = db.connect().prepareStatement("select * from ressources");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImageView imgView = img(rs, "image");
                ImageView imgView2 = img(rs, "qr");
                HBox hbox = action();
                list.add(new Get_parts(imgView, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7).toString(), rs.getString(10), hbox, imgView2));
                table.setItems(list);
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void ajout(List<Get_parts> list, ImageView img, String internal_pn, String name, String label, String classification, String origin, String storage, String created_on, String description) {
        list.clear();
        list.add(new Get_parts(img, internal_pn, name, label, classification, origin, storage, created_on, description, null, null));
    }


    public HBox action() {
        FontAwesomeIconView qr = new FontAwesomeIconView();
        FontAwesomeIconView pdf = new FontAwesomeIconView();
        HBox hbox = new HBox();
        pdf.setGlyphName("FILE");
        qr.setGlyphName("QRCODE");
        pdf.setSize("1.5em");
        qr.setSize("1.5em");
        pdf.setCursor(Cursor.HAND);
        qr.setCursor(Cursor.HAND);
        pdf.setFill(Color.web("#435B7B"));
        qr.setFill(Color.web("#435B7B"));
        hbox.getChildren().add(qr);
        hbox.getChildren().add(pdf);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crud.setVisible(false);
        String[] s = LoginController.getS();
        List ls = List.of(s[2].replaceAll("[{}]", "").split(","));
        if (ls.get(1).equals("oui")) {
            crud.setVisible(true);
        }


        internal_pn.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("internal_pn"));
        nom.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("name"));
        label.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("label"));
        classification.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("classification"));
        origin.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("origin"));
        storage.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("storage"));
        desc.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("description"));
        action.setCellValueFactory(new PropertyValueFactory<Get_parts, HBox>("action"));
        img.setCellValueFactory(new PropertyValueFactory<Get_parts, ImageView>("img"));
        date_creation.setCellValueFactory(new PropertyValueFactory<Get_parts, String>("created_on"));

        load();
        table.setOnMouseClicked(eventHandler -> {
            for (Get_parts list : table.getSelectionModel().getSelectedItems()) {
                for (int i = 1; i <= 1; i++) {
                    list.getAction().getChildren().get(0).setOnMouseClicked(e -> {
                        DB db = new DB();
                        PopOver popup = new PopOver();
                        try {
                            PreparedStatement ps = db.connect().prepareStatement("select qr from ressources where internal_pn=?");
                            ps.setString(1, list.getInternal_pn());
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                ImageView imgView2 = img(rs, "qr");
                                imgView2.prefHeight(100.0);
                                imgView2.prefWidth(100.0);
                                popup.setContentNode(imgView2);
                                popup.show(list.getAction().getChildren().get(0));
                                popup.setAutoHide(true);
                            }

                        } catch (Exception exx) {
                        }
                    });
                    list.getAction().getChildren().get(1).setOnMouseClicked(e -> {
                        String query = "select datasheet from ressources where internal_pn =?";


                        try {
                            DB db = new DB();
                            PreparedStatement preparedStatement = db.connect().prepareStatement(query);
                            preparedStatement.setString(1, list.getInternal_pn());
                            ResultSet rs = preparedStatement.executeQuery();


                                    FileOutputStream fos = new FileOutputStream(list.getName() + " datasheet.pdf");
                                    rs.next();
                                    byte[] fileBytes = rs.getBytes(1);
                                    if(fileBytes==null){

                                            PopOver popup = new PopOver();
                                            Label lb=new Label();
                                            lb.setText("Aucun fichier trouvé");
                                            lb.setTextFill(Color.BLACK);

                                            popup.setContentNode(lb);
                                            popup.show(list.getAction().getChildren().get(1));
                                            popup.setAutoHide(true);
                                    }
                                    else{fos.write(fileBytes);

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Fichier enregistré avec succès");
                                    alert.show();}




                        } catch (Exception ex) {
                        }


                    });

                    update.setOnMouseClicked(ev -> {
                        list2.clear();
                        ajout(list2, list.getImg(), list.getInternal_pn(), list.getName(), list.getLabel(), list.getClassification(), list.getOrigin(), list.getStorage(), list.getCreated_on(), list.getDescription());
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
                        }
                    });


                }


            }
        });
        add.setOnMouseClicked(e -> {
            list2.clear();
            FXMLLoader loader = new FXMLLoader();
            try {
                ScrollPane pane = loader.load(getClass().getResource("add_part.fxml").openStream());
                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(pane));
                stage.show();


            } catch (IOException ex) {
            }
        });

    }

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


}
