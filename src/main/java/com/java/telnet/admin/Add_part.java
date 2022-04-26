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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private AnchorPane label_box;


    @FXML
    private ChoiceBox<String> classification;
    @FXML
    private Label txt;
    @FXML
    private TextField id;

    @FXML
    private ImageView img;

    @FXML
    private TextArea descr;

    @FXML
    private TextField nom, store;
    @FXML
    private FontAwesomeIconView add;
    @FXML
    private ChoiceBox<String> origine;
    @FXML
    Button submit, update;
    MenuButton labelbtn = new MenuButton("Label");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {id.setEditable(true);
        labelbtn.setLayoutX(480.0);labelbtn.setLayoutY(30.0);
        if (Parts.list2.isEmpty()) {
            submit.setVisible(true);
            update.setVisible(false);
            update.setManaged(false);

        } else {
            update.setVisible(true);
            submit.setVisible(false);
            submit.setManaged(false);
            id.setText(Parts.list2.get(0).getInternal_pn());id.setEditable(false);
            nom.setText(Parts.list2.get(0).getName());
            labelbtn.setText(Parts.list2.get(0).getLabel());
            store.setText(Parts.list2.get(0).getStorage());
            descr.setText(Parts.list2.get(0).getDescription());
            if (Parts.list2.get(0).getClassification().equals("Restreinte"))
                classification.getSelectionModel().selectFirst();
            else classification.getSelectionModel().selectLast();
            if (Parts.list2.get(0).getOrigin().equals("Externe"))
                origine.getSelectionModel().selectFirst();
            else origine.getSelectionModel().selectLast();



            try {
                load_img();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        img.setImage(new Image(getClass().getResource("carte.jpg").toString()));
        classification.getItems().addAll("Restreinte", "Usage interne");
        origine.getItems().addAll("Externe", "Interne");

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
        TreeItem<String> list13 = new TreeItem<>("Autre");
        list12.getChildren().addAll(new TreeItem<String>("Accelerometer"), new TreeItem<String>("Current"), new TreeItem<String>("Gyroscope"), new TreeItem<String>("Humidity"), new TreeItem<String>("Light"), new TreeItem<String>("Magnetometer"), new TreeItem<String>("Pressure"), new TreeItem<String>("Temperature"));
        root.getChildren().addAll(list00, list0, list1, list22, list3, list4, list5, list7, list8, list9, list11, list12, list13);
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
                    }
                } catch (Exception e) {
                }
            }

        });
    }

    File file2 = null;
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

    Boolean v = true;

    public void submit() throws SQLException, IOException, WriterException {

        if (id.getText().isEmpty()) {
            v = false;
        }
        if (nom.getText().isEmpty()) {
            v = false;
        }
        if (labelbtn.getText().isEmpty()) {
            v = false;
        }

        if (v) {
            DB db = new DB();
            CallableStatement call = db.connect().prepareCall("INSERT INTO public.ressources(internal_pn, name, label, classification, origin, storage, qr, image, \"desc\", datasheet)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            call.setString(1, id.getText());
            call.setString(2, nom.getText());
            call.setString(3, labelbtn.getText());
            call.setString(4, classification.getSelectionModel().getSelectedItem());
            call.setString(5, origine.getSelectionModel().getSelectedItem());
            call.setString(6, store.getText());
            if (file1 != null) {
                FileInputStream fin = new FileInputStream(file1);
                call.setBinaryStream(8, fin, (int) file1.length());
            } else {
                File f = new File("src/main/resources/com/java/telnet/admin/carte.jpg");
                FileInputStream fin = new FileInputStream(f);
                call.setBinaryStream(8, fin, (int) f.length());
            }
            call.setString(9, descr.getText());
            call.setBytes(7, getQRCodeImage(id.getText(), 250, 250));
            if (file2 != null) call.setBytes(10, getByteArrayFromFile());
            else call.setBytes(10, null);
            call.execute();
            call.close();
            Stage stage = (Stage) store.getScene().getWindow();
            stage.close();
        }
    }

    public void update() {

    }

    public void load_img() throws IOException, SQLException {
        DB db = new DB();
        PreparedStatement ps = db.connect().prepareStatement("select image from ressources where internal_pn=?");
        ps.setString(1, Parts.list2.get(0).getInternal_pn());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            InputStream is = rs.getBinaryStream(1);
            OutputStream os = new FileOutputStream(new File("src/main/resources/com/java/telnet/admin/photo.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();


        }
        ps.close();


    }
//    CallableStatement call2 = db.connect().prepareCall("call history(?,?,?)");
//                call2.setString(1,internal_pn.getSelectionModel().getSelectedItem());
//                call2.setString(2,LoginController.name);
//                call2.setString(3,"Ajout un composant au nomeclature du projet "+Projet.pr);
//                call2.execute();
//                call2.close();
}
