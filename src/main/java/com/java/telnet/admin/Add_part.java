package com.java.telnet.admin;


import com.google.zxing.WriterException;
import com.java.telnet.DB;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_achat;
import com.java.telnet.admin.models.Get_parts;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Document;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.java.telnet.admin.Qr.getQRCodeImage;

public class Add_part implements Initializable {
    @FXML
    private ChoiceBox<String> access;

    @FXML
    private HBox label_box;
    @FXML
    private ChoiceBox<String> cat;

    @FXML
    private ChoiceBox<String> classification;
    @FXML
    private Label txt;
    @FXML
    private TextField id, comment;

    @FXML
    private ImageView img;

    @FXML
    private TextArea descr;
    @FXML
    private HBox store;

    @FXML
    private TextField nom;
    @FXML
    private FontAwesomeIconView add;
    @FXML
    private ChoiceBox<String> origine;
    MenuButton stockbtn = new MenuButton("Storage");
    MenuButton labelbtn = new MenuButton("Label");


//    public void add_desc() {
//        FXMLLoader loader = new FXMLLoader();
//        try {
//            Pane pane = loader.load(getClass().getResource("desc_part.fxml").openStream());
//            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(new Scene(pane));
//            stage.show();
//        } catch (IOException e) {
//        }
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final TreeView<String> tree1 = new TreeView<String>();
        TreeItem<String> root1 = new TreeItem<>("");
        root1.setExpanded(true);
        TreeItem<String> st1 = new TreeItem<>("Stockage Principale");
        st1.getChildren().addAll(new TreeItem<String>("rang A"), new TreeItem<String>("Rang B"), new TreeItem<String>("Rang C"));
        root1.getChildren().addAll(st1);
        tree1.setRoot(root1);
        tree1.setShowRoot(false);
        CustomMenuItem customMenuItem2 = new CustomMenuItem(tree1);
        customMenuItem2.setHideOnClick(false);
        stockbtn.getItems().add(customMenuItem2);
        store.getChildren().add(stockbtn);
        customMenuItem2.setOnAction(e -> {
            if (tree1.getSelectionModel().getSelectedItem().isLeaf()) {
                stockbtn.setText(tree1.getSelectionModel().getSelectedItem().getValue());
                stockbtn.hide();
            } else ;

        });
        cat.getItems().addAll("Matériel", "Logiciel", "Bureautique");
        classification.getItems().addAll("Restreinte", "Usage interne");
        origine.getItems().addAll("Externe", "Interne");
        access.getItems().addAll("Team members");
        System.out.println(Users.getList());
        cat.setOnAction(event -> {
            if (cat.getSelectionModel().isSelected(0)) label_box.setDisable(false);
            else label_box.setDisable(true);
        });
        img.setImage(new Image(getClass().getResource("carte.jpg").toString()));

        final TreeView<String> tree = new TreeView<String>();
        TreeItem<String> root = new TreeItem<>("");
        root.setExpanded(true);
        TreeItem<String> list00 = new TreeItem<>("Audio");
        list00.getChildren().addAll(new TreeItem<String>("Amplifier"), new TreeItem<String>("Buzzer"), new TreeItem<String>("Speaker"));
        TreeItem<String> list1 = new TreeItem<>("Resistor");
        TreeItem<String> list22 = new TreeItem<>("Capacitor");
        TreeItem<String> list3 = new TreeItem<>("Inductor");
        TreeItem<String> list4 = new TreeItem<>("Data converter");
        list4.getChildren().addAll(new TreeItem<String>("ADC"), new TreeItem<String>("DAC"));
        TreeItem<String> list5 = new TreeItem<>("Diode");
        list5.getChildren().addAll(new TreeItem<String>("LED"), new TreeItem<String>("Schottcky"), new TreeItem<String>("Zener"));
        TreeItem<String> list7 = new TreeItem<>("Interface");
        list7.getChildren().addAll(new TreeItem<String>("I2C"), new TreeItem<String>("SPI"), new TreeItem<String>("USB"));
        TreeItem<String> list8 = new TreeItem<>("Memory");
        list8.getChildren().addAll(new TreeItem<String>("EEPROM"), new TreeItem<String>("SRAM"));
        TreeItem<String> list9 = new TreeItem<>("Microcontroller");
        list9.getChildren().addAll(new TreeItem<String>("AVR"), new TreeItem<String>("EFM32"), new TreeItem<String>("MSP430"), new TreeItem<String>("PIC"), new TreeItem<String>("STM32"));
        TreeItem<String> list0 = new TreeItem<>("Power");
        list0.getChildren().addAll(new TreeItem<String>("LDO"), new TreeItem<String>("Switch Converter"));
        TreeItem<String> list11 = new TreeItem<>("Transistor");
        list11.getChildren().addAll(new TreeItem<String>("BJT"), new TreeItem<String>("JFET"), new TreeItem<String>("MOS"));
        TreeItem<String> list12 = new TreeItem<>("Sensor");
        list12.getChildren().addAll(new TreeItem<String>("Accelerometer"), new TreeItem<String>("Current"), new TreeItem<String>("Gyroscope"), new TreeItem<String>("Humidity"), new TreeItem<String>("Light"), new TreeItem<String>("Magnetometer"), new TreeItem<String>("Pressure"), new TreeItem<String>("Temperature"));
        root.getChildren().addAll(list00, list0, list1, list22, list3, list4, list5, list7, list8, list9, list11, list12);
        tree.setRoot(root);
        tree.setShowRoot(false);
        CustomMenuItem customMenuItem = new CustomMenuItem(tree);
        customMenuItem.setHideOnClick(false);
        labelbtn.getItems().add(customMenuItem);
        label_box.getChildren().add(labelbtn);
        customMenuItem.setOnAction(e -> {
            if (tree.getSelectionModel().getSelectedItem().isLeaf()) {
                labelbtn.setText(tree.getSelectionModel().getSelectedItem().getValue());
                labelbtn.hide();
            } else ;

        });
        txt.setManaged(false);

        add.setOnMouseClicked(e -> {
            img();
        });

        id.focusedProperty().addListener((observableValue, old, newval) -> {
            if (old) {
                DB db = new DB();
                try {
                    PreparedStatement ps = db.connect().prepareStatement("select * from ressources where internal_pn=?");
                    ps.setString(1, id.getText());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        txt.setVisible(true);
                        txt.setManaged(true);
                    } else {
                        txt.setVisible(false);
                        txt.setManaged(false);
                        Boolean verif = true;
                    }
                } catch (Exception e) {
                }
            }

        });
    }

    File file2=null;
    public File file1 = null;

    public void img() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            img.setImage(image1);
            file1 = new File(file.toURI().getPath());
        }
    }

    private byte[] getByteArrayFromFile() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream in = new FileInputStream(file2);
        final byte[] buffer = new byte[500];

        int read = -1;
        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }
        in.close();

        return baos.toByteArray();
    }

    public File load() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open PDF file...");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File f = fc.showOpenDialog(null);
        file2 = new File(f.toURI().getPath());

        return file2;

    }

    public void submit() throws SQLException, IOException, WriterException {
        DB db = new DB();
        CallableStatement call = db.connect().prepareCall("call add_part(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        call.setString(1, id.getText());
        call.setString(2, nom.getText());
        call.setString(3, labelbtn.getText());
        call.setString(4, classification.getSelectionModel().getSelectedItem());
        call.setString(5, access.getSelectionModel().getSelectedItem());
        call.setString(6, origine.getSelectionModel().getSelectedItem());
        call.setString(7, stockbtn.getText());
        call.setString(8, comment.getText());
        call.setString(9, cat.getSelectionModel().getSelectedItem());
        if (file1 != null) {
            FileInputStream fin = new FileInputStream(file1);
            call.setBinaryStream(10, fin, (int) file1.length());
        } else {
            BufferedImage bImage = ImageIO.read(new File("carte.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            byte[] data = bos.toByteArray();
            call.setBytes(10, data);
        }
        call.setString(13, descr.getText());
        call.setBytes(11, getQRCodeImage(id.getText(), 250, 250));
        call.setBytes(12, getByteArrayFromFile());
        call.execute();
        call.close();

    }

//    CallableStatement call2 = db.connect().prepareCall("call history(?,?,?)");
//                call2.setString(1,internal_pn.getSelectionModel().getSelectedItem());
//                call2.setString(2,LoginController.name);
//                call2.setString(3,"Ajout un composant au nomeclature du projet "+Projet.pr);
//                call2.execute();
//                call2.close();
}
