package com.java.telnet.admin;

import com.google.zxing.WriterException;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Add_bom implements Initializable {
    @FXML
    private Button submit;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private TextField design;

    private String internal_pn, name;

    @FXML
    private HBox label, id, stat_box, qty;

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
        label.setDisable(true);
        stat_box.setDisable(true);
        part_name.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            part_name.setValue(newText);
            quantite.clear();
            quantite.setEditable(true);

        });
        submit.setOnMouseClicked(event -> {
            if (Projet.list4.isEmpty()) {
                try {
                    submit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    update();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
        if (Projet.list4.isEmpty() == false) {
            part_name.getEditor().setText(Projet.list4.get(0).getNom());
            if (Projet.list4.get(0).getCat().equals("Matériel"))
                cat.getSelectionModel().selectLast();
            else if (Projet.list4.get(0).getCat().equals("Logiciel")) cat.getSelectionModel().select(2);
            else cat.getSelectionModel().selectFirst();
            stat.setText(Projet.list4.get(0).getEtat());
            descr.setText(Projet.list4.get(0).getDesc());
            design.setText(Projet.list4.get(0).getDesign());
            label.setDisable(false);
            mb.setText(Projet.list4.get(0).getLabel());
            stat_box.setDisable(false);
            stat.setText(Projet.list4.get(0).getEtat());
            quantite.setText(Projet.list4.get(0).getQty().toString());

        }


        txt1.setManaged(false);
        txt2.setManaged(false);
        txt3.setManaged(false);
        quantite.setText("");
        quantite.setEditable(true);

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
        try {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("select name,internal_pn from ressources");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                part_name.getItems().add(rs.getString(1) + "      " + rs.getString(2));
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        part_name.setOnAction(e -> {
            if (part_name.getSelectionModel().getSelectedItem().isEmpty() == false) {
                try {
                    DB db = new DB();

                    PreparedStatement ps = db.connect().prepareStatement("select internal_pn,label,stat,category from ressources where name=?");
                    ps.setString(1, part_name.getSelectionModel().getSelectedItem().toString().split("      ")[0]);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        internal_pn = rs.getString(1);
                        stat_box.setDisable(false);
                        label.setDisable(false);
                        stat.setText(rs.getString(3));
                        mb.setText(rs.getString(2));
                        quantite.setText("1");
                        quantite.setEditable(false);
                        if (cat.getSelectionModel().equals("Matériel"))
                            cat.getSelectionModel().selectLast();
                        else if (cat.getSelectionModel().equals("Logiciel")) cat.getSelectionModel().select(2);
                        else cat.getSelectionModel().selectFirst();
                    }

                    ps.close();

                } catch (Exception ex) {
                }
            } else {
                quantite.clear();
                quantite.setEditable(true);
            }
        });

        cat.setOnAction(event -> {
            if (cat.getSelectionModel().isSelected(0)) label.setDisable(false);
            else label.setDisable(true);
        });

    }


    Boolean v;

    public void submit() throws SQLException {
        v = true;
        txt1.setVisible(false);
        txt2.setVisible(false);
        txt3.setVisible(false);

        if (part_name.getValue().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }
        if (cat.getSelectionModel().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;
        }
        if (quantite.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;
        }

        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources where name=?");
            ps.setString(1, part_name.getSelectionModel().getSelectedItem().toString().split("      ")[0]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) < 2) {
                    CallableStatement call2 = db.connect().prepareCall("call achat(?,?,?,?,?)");
                    call2.setString(1, part_name.getValue());
                    call2.setInt(2, Integer.parseInt(quantite.getText()));
                    call2.setString(3, descr.getText());
                    call2.setString(4, Projet.pr);
                    call2.setString(5, LoginController.name);
                    call2.execute();
                    call2.close();
                    CallableStatement call = db.connect().prepareCall("call history(?,?,?)");
                    call.setString(1, internal_pn);
                    call.setString(2, LoginController.name);
                    call.setString(3, "Ajout un composant au liste des achats ");
                    call.execute();
                    PreparedStatement call1 = db.connect().prepareStatement("INSERT INTO bom( \"part_id \", designators, qty, comment, projet, resp, label, name,cat) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?);");
                    call1.setString(1, internal_pn);
                    call1.setString(2, design.getText());
                    call1.setInt(3, Integer.parseInt(quantite.getText()));
                    call1.setString(4, descr.getText());
                    call1.setString(5, Projet.pr);


                    call1.setString(6, LoginController.name);
                    call1.setString(7, mb.getText());
                    call1.setString(8, part_name.getSelectionModel().getSelectedItem());
                    call1.setString(9, cat.getSelectionModel().getSelectedItem());
                    call1.executeUpdate();
                    CallableStatement call3 = db.connect().prepareCall("call history(?,?,?)");
                    call3.setString(1, internal_pn);
                    call3.setString(2, LoginController.name);
                    call3.setString(3, "Ajout un composant au nomeclature du projet " + Projet.pr);
                    call3.execute();
                    call3.close();

                    PreparedStatement ps1 = db.connect().prepareStatement("delete from ressources WHERE internal_pn=?");
                    ps1.setString(1, internal_pn);
                    ps1.executeUpdate();
                    ps1.close();
                    call.close();
                    Stage stage = (Stage) submit.getScene().getWindow();
                    stage.close();
                } else {
                    PreparedStatement call1 = db.connect().prepareStatement("INSERT INTO bom( \"part_id \", designators, qty, comment, projet, resp, label, name) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
                    call1.setString(1, internal_pn);
                    call1.setString(2, design.getText());
                    call1.setInt(3, Integer.parseInt(quantite.getText()));
                    call1.setString(4, descr.getText());
                    call1.setString(5, Projet.pr);
                    call1.setString(6, LoginController.name);
                    call1.setString(7, mb.getText());
                    call1.setString(8, part_name.getSelectionModel().getSelectedItem());
                    call1.executeUpdate();
                    CallableStatement call3 = db.connect().prepareCall("call history(?,?,?)");
                    call3.setString(1, internal_pn);
                    call3.setString(2, LoginController.name);
                    call3.setString(3, "Ajout un composant au nomeclature du projet " + Projet.pr);
                    call3.execute();
                    call3.close();

                    PreparedStatement ps1 = db.connect().prepareStatement("delete from ressources WHERE internal_pn=?");
                    ps1.setString(1, internal_pn);
                    ps1.executeUpdate();
                    ps1.close();

                    call1.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("L'utilisateur  a été mis à jour avec succes");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Stage stage = (Stage) submit.getScene().getWindow();
                        stage.close();
                    }
                }
            }
        }
    }

    public void update() throws SQLException {
        v = true;
        txt1.setVisible(false);
        txt2.setVisible(false);
        txt3.setVisible(false);

        if (part_name.getValue().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }
        if (cat.getSelectionModel().isEmpty()) {
            txt2.setVisible(true);
            txt2.setManaged(true);
            v = false;
        }
        if (quantite.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;
        }

        if (v) {
            DB db = new DB();
            PreparedStatement call1 = db.connect().prepareStatement("UPDATE bom SET \"part_id \"=?, designators=?, qty=?, comment=?, projet=?, resp=?,label=?, name=? WHERE id=?;");
            call1.setString(1, Projet.list4.get(0).getNum());
            call1.setString(2, design.getText());
            call1.setInt(3, Integer.parseInt(quantite.getText()));
            call1.setString(4, descr.getText());
            call1.setString(5, Projet.pr);
            call1.setString(6, LoginController.name);
            call1.setString(7, mb.getText());
            call1.setString(8, part_name.getSelectionModel().getSelectedItem());
            call1.setInt(9, Projet.list4.get(0).getId());
            call1.executeUpdate();
            CallableStatement call3 = db.connect().prepareCall("call history(?,?,?)");
            call3.setString(1, Projet.list4.get(0).getNum());
            call3.setString(2, LoginController.name);
            call3.setString(3, "Mise à jour d'un composant au nomeclature du projet " + Projet.pr);
            call3.execute();
            call3.close();
            call1.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le composant a été mis à jour avec succès");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
        }
    }
}



