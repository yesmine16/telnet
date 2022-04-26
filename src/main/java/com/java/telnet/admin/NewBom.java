package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewBom implements Initializable {
    @FXML
    private Button submit;


    @FXML
    private TextField design;



    @FXML
    private HBox label;

    @FXML
    private TextField part_name;
    MenuButton mb = new MenuButton("Label");

    @FXML
    private TextArea descr;
    @FXML
    private TextField quantite;


    @FXML
    private Text txt1;



    @FXML
    private Text txt3;
    @FXML
    private FontAwesomeIconView plus, moins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        submit.setOnMouseClicked(event -> {

            try {
                submit();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        });

        txt1.setManaged(false);
        txt3.setManaged(false);
        final TreeView<String> tree = new TreeView<String>();
        TreeItem<String> root = new TreeItem<>("");
        root.setExpanded(true);
        TreeItem<String> list = new TreeItem<>("Audio");
        list.getChildren().addAll(new TreeItem<String>("Amplifier"), new TreeItem<String>("Buzzer"), new TreeItem<String>("Speaker"));
        TreeItem<String> list1 = new TreeItem<>("Resistor");
        TreeItem<String> list2 = new TreeItem<>("Capacitor");
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
        TreeItem<String> list13= new TreeItem<>("Autre");

        list12.getChildren().addAll(new TreeItem<String>("Accelerometer"), new TreeItem<String>("Current"), new TreeItem<String>("Gyroscope"), new TreeItem<String>("Humidity"), new TreeItem<String>("Light"), new TreeItem<String>("Magnetometer"), new TreeItem<String>("Pressure"), new TreeItem<String>("Temperature"));
        root.getChildren().addAll(list, list0, list1, list2, list3, list4, list5, list7, list8, list9, list11, list12,list13);
        tree.setRoot(root);
        tree.setShowRoot(false);
        CustomMenuItem customMenuItem = new CustomMenuItem(tree);
        customMenuItem.setHideOnClick(false);
        mb.getItems().add(customMenuItem);
        label.getChildren().add(mb);
        customMenuItem.setOnAction(e -> {
            if (tree.getSelectionModel().getSelectedItem().isLeaf()) {
                mb.setText(tree.getSelectionModel().getSelectedItem().getValue());
                mb.hide();
            } else ;

        });


        plus.setOnMouseClicked(ev -> {

            quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) + 1));


        });

        moins.setOnMouseClicked(ev -> {
            if (Integer.parseInt(quantite.getText()) != 0) {
                quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) - 1));
            }
        });
    }


    Boolean v;

    public void submit() throws SQLException {
        v = true;
        txt1.setVisible(false);
        txt3.setVisible(false);

        if (part_name.getText().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }

        if (quantite.getText().equals("0")) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;
        }

        if (v) {
            DB db = new DB();

            CallableStatement call2 = db.connect().prepareCall("call achat(?,?,?,?,?)");
            call2.setString(1, part_name.getText());
            call2.setInt(2, Integer.parseInt(quantite.getText()));
            call2.setString(3, descr.getText());
            call2.setString(4, Projet.pr);
            call2.setString(5, LoginController.name);
            call2.execute();
            call2.close();
            PreparedStatement call = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES (?,?);");
            call.setString(1, LoginController.name);
            call.setString(2, "Ajouter un nouveau composant au liste des achats");
            call.executeUpdate();
            PreparedStatement call1 = db.connect().prepareStatement("INSERT INTO bom( \"part_id \", designators, qty, comment, projet, resp, label, name) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
            call1.setString(1, "-");
            call1.setString(2, design.getText());
            call1.setInt(3, Integer.parseInt(quantite.getText()));
            call1.setString(4, descr.getText());
            call1.setString(5, Projet.pr);


            call1.setString(6, LoginController.name);
            call1.setString(7, mb.getText());
            call1.setString(8, part_name.getText());
            call1.executeUpdate();
            PreparedStatement call3 = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES (?,?);");
            call3.setString(1, LoginController.name);
            call3.setString(2, "Ajouté au projet " + Projet.pr);
            call3.executeUpdate();
            call3.close();

            call.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Validé avec succès !");
            alert.show();
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();


        }
    }


}



