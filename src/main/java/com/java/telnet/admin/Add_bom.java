package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_project;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Add_bom implements Initializable {
    @FXML
    private Button submit;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private TextField design;

    @FXML
    private ChoiceBox<String> internal_pn;

    @FXML
    private HBox label, id, stat_box;

    @FXML
    private ComboBox<String> part_name;
    MenuButton mb = new MenuButton("Label");
    @FXML
    private TextArea descr;
    @FXML
    private TextField quantite;

    @FXML
    private TextField stat;
    @FXML
    private Text txt1;

    @FXML
    private Text txt2;

    @FXML
    private Text txt3;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        part_name.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            part_name.setValue(newText);
        });
        txt1.setManaged(false);
        txt2.setManaged(false);
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

        list12.getChildren().addAll(new TreeItem<String>("Accelerometer"), new TreeItem<String>("Current"), new TreeItem<String>("Gyroscope"), new TreeItem<String>("Humidity"), new TreeItem<String>("Light"), new TreeItem<String>("Magnetometer"), new TreeItem<String>("Pressure"), new TreeItem<String>("Temperature"));
        root.getChildren().addAll(list, list0, list1, list2, list3, list4, list5, list7, list8, list9, list11, list12);
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
        cat.getItems().addAll("Matériel", "Logiciel", "Bureautique");
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select name from ressources where av='Disponible'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                part_name.getItems().add(rs.getString(1));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        part_name.setOnAction(e -> {
            if (part_name.getSelectionModel().getSelectedItem().isEmpty() == false) {
                internal_pn.getItems().clear();
                try {
                    PreparedStatement ps = db.connect().prepareStatement("select internal_pn,label,stat,category from ressources where name=?");
                    ps.setString(1, part_name.getSelectionModel().getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        internal_pn.getItems().add(rs.getString(1));
                        id.setDisable(false);
                        stat_box.setDisable(false);
                        label.setDisable(false);
                        stat.setText(rs.getString(3));
                        mb.setText(rs.getString(2));
                        if (cat.getSelectionModel().equals("Matériel"))
                            cat.getSelectionModel().selectLast();
                        else if (cat.getSelectionModel().equals("Logiciel")) cat.getSelectionModel().select(2);
                        else cat.getSelectionModel().selectFirst();
                    }
                    if (rs.next()) {


                    }


                    ps.close();

                } catch (Exception ex) {
                }
            }
        });

        cat.setOnAction(event -> {
            if (cat.getSelectionModel().isSelected(0)) label.setDisable(false);
            else label.setDisable(true);
        });

    }

    public void submit() throws SQLException {
        if (part_name.getValue().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
        } else if (cat.getSelectionModel().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
        } else if (quantite.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
        } else {

            DB db = new DB();
            if (id.isDisabled()) {
                CallableStatement call2 = db.connect().prepareCall("call achat(?,?,?,?,?)");
                call2.setString(1, part_name.getValue());
                call2.setInt(2, Integer.parseInt(quantite.getText()));
                call2.setString(3, descr.getText());
                call2.setString(4, Projet.pr);
                call2.setString(5, LoginController.name);
                call2.execute();
                call2.close();
                CallableStatement call = db.connect().prepareCall("call history(?,?,?)");
                call.setString(1,internal_pn.getSelectionModel().getSelectedItem());
                call.setString(2,LoginController.name);
                call.setString(3,"Ajout un composant au liste des achat ");
                call.execute();
                call.close();

                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
            else{
            CallableStatement call = db.connect().prepareCall("call add_bom(?,?,?,?,?,?,?,?,?,?)");
            call.setString(1, internal_pn.getSelectionModel().getSelectedItem());
            call.setString(2, design.getText());
            call.setInt(3, Integer.parseInt(quantite.getText()));
            call.setString(4, descr.getText());
            call.setString(5, Projet.pr);

            PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources where name=?");
            ps.setString(1, part_name.getSelectionModel().getSelectedItem());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                call.setInt(6, rs.getInt(1));
                call.setInt(7, Math.abs(rs.getInt(1) - Integer.parseInt(quantite.getText())));
            }
            call.setString(8, LoginController.name);
            call.setString(9, mb.getText());
            call.setString(10, part_name.getSelectionModel().getSelectedItem());
            call.execute();
                CallableStatement call2 = db.connect().prepareCall("call history(?,?,?)");
                call2.setString(1,internal_pn.getSelectionModel().getSelectedItem());
                call2.setString(2,LoginController.name);
                call2.setString(3,"Ajout un composant au nomeclature du projet "+Projet.pr);
                call2.execute();
                call2.close();

            PreparedStatement ps1 = db.connect().prepareStatement("UPDATE ressources SET av='Réservé' WHERE internal_pn=?");
            ps1.setString(1, internal_pn.getSelectionModel().getSelectedItem());
            ps1.executeUpdate();
            ps1.close();
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
            call.close();}

        }
    }


}
