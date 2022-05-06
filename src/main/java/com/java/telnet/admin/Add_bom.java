package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Add_bom implements Initializable {
    @FXML
    private Button submit;

    @FXML
    private Hyperlink link;
    @FXML
    private TextField design;

    private String internal_pn;

    @FXML
    private AnchorPane label;

    @FXML
    private ChoiceBox<String> part_name;
    MenuButton mb = new MenuButton("Label");

    @FXML
    private TextArea descr;
    @FXML
    private TextField quantite;


    @FXML
    private Text txt1;

    @FXML
    private Text ref;


    @FXML
    private FontAwesomeIconView plus, moins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mb.setLayoutX(480.0);
        mb.setLayoutY(30.0);
        txt1.setVisible(false);
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

            descr.setText(Projet.list4.get(0).getDesc());
            design.setText(Projet.list4.get(0).getDesign());
            mb.setText(Projet.list4.get(0).getLabel());
            quantite.setText(Projet.list4.get(0).getQty().toString());

        }


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
        TreeItem<String> list13= new TreeItem<>("Autre");
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

        try {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("select name,internal_pn from ressources");
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
                try {
                    DB db = new DB();

                    PreparedStatement ps = db.connect().prepareStatement("select internal_pn,label from ressources where name=?");
                    ps.setString(1, part_name.getSelectionModel().getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        internal_pn = rs.getString(1);
                        mb.setText(rs.getString(2));
                        quantite.setText("1");

                    }

                    ps.close();

                } catch (Exception ex) {
                }
            }
        });
        ref.setVisible(false);
        plus.setOnMouseClicked(ev -> {
            ref.setVisible(false);

            if (part_name.getSelectionModel().getSelectedItem() != null) {

                try {
                    DB db = new DB();
                    PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources where name=?");
                    ps.setString(1, part_name.getSelectionModel().getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if (Integer.parseInt(quantite.getText()) < rs.getInt(1)) {
                            quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) + 1));
                        } else {
                            ref.setText("Nombre insufissant");
                            ref.setVisible(true);
                        }
                    }

                    ps.close();

                } catch (Exception ex) {
                }
            } else {
                ref.setText("Vous devez choisir un composant d'abord");
                ref.setVisible(true);
            }

        });

        moins.setOnMouseClicked(ev -> {
            ref.setVisible(false);
            if (part_name.getSelectionModel().getSelectedItem() != null) {


                try {
                    DB db = new DB();
                    PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources where name=?");
                    ps.setString(1, part_name.getSelectionModel().getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if (Integer.parseInt(quantite.getText()) != 0) {
                            quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) - 1));
                        }
                        if (Integer.parseInt(quantite.getText()) < rs.getInt(1)) {
                            ref.setVisible(false);
                        }
                    }
                    ps.close();

                } catch (Exception ex) {
                }

            } else {
                ref.setText("Vous devez choisir un composant d'abord");
                ref.setVisible(true);
            }

        });

        link.setOnMouseClicked(ev -> {

            FXMLLoader loader = new FXMLLoader();
            try {
                AnchorPane pane = loader.load(getClass().getResource("new_bom.fxml").openStream());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Nouveau projet");
                pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                stage.setScene(new Scene(pane));
                stage.show();
            } catch (IOException e) {
            }
            Stage st= (Stage) submit.getScene().getWindow();
            st.close();
        });
    }


    Boolean v;

    public void submit() throws SQLException {
        v = true;
        txt1.setVisible(false);
        ref.setVisible(false);

        if (part_name.getValue().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }

        if (quantite.getText().equals("0")) {
            ref.setVisible(true);
            ref.setText("champ obligatoire !");
            v = false;
        }


        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("select count(*) from ressources where name=?");
            ps.setString(1, part_name.getSelectionModel().getSelectedItem());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) < 2) {
                    PreparedStatement call2 = db.connect().prepareStatement("INSERT INTO public.\"purchase_list \"( part_name, qty_needed, \"description \", resp, projet)VALUES (?, ?, ?, ?, ?);");
                    call2.setString(1, part_name.getValue());
                    call2.setInt(2, Integer.parseInt(quantite.getText()));
                    call2.setString(3, descr.getText());
                    call2.setString(4, LoginController.name);
                    call2.setInt(5, Projet.pr);
                    call2.executeQuery();
                    call2.close();
                    PreparedStatement call = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES ( ?, ?);");
                    call.setString(1, LoginController.name);
                    call.setString(2, "Ajout un composant au liste des achats ");
                    call.executeQuery();
                    PreparedStatement call1 = db.connect().prepareStatement("INSERT INTO bom( \"part_id \", designators, qty, comment, projet, resp, label, name) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
                    call1.setString(1, internal_pn);
                    call1.setString(2, design.getText());
                    call1.setInt(3, Integer.parseInt(quantite.getText()));
                    call1.setString(4, descr.getText());
                    call1.setInt(5, Projet.pr);


                    call1.setString(6, LoginController.name);
                    call1.setString(7, mb.getText());
                    call1.setString(8, part_name.getSelectionModel().getSelectedItem());
                    call1.executeUpdate();
                    PreparedStatement call3 = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES ( ?, ?);");
                    call3.setString(1, LoginController.name);
                    call3.setString(2, "Ajouté au projet " + Projet.pr);
                    call3.executeQuery();
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
                    call1.setInt(5, Projet.pr);
                    call1.setString(6, LoginController.name);
                    call1.setString(7, mb.getText());
                    call1.setString(8, part_name.getSelectionModel().getSelectedItem());
                    call1.executeUpdate();
                    PreparedStatement call3 = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES ( ?, ?);");
                    call3.setString(1, LoginController.name);
                    call3.setString(2, "Ajouté au projet " + Projet.pr);
                    call3.executeQuery();
                    call3.close();

                    PreparedStatement ps1 = db.connect().prepareStatement("delete from ressources WHERE internal_pn=?");
                    ps1.setString(1, internal_pn);
                    ps1.executeUpdate();
                    ps1.close();

                    call1.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Opération réussite !");
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
        ref.setVisible(false);
        if (part_name.getValue().isEmpty()) {
            txt1.setVisible(true);
            v = false;
        }

        if (quantite.getText().isEmpty()) {
            ref.setVisible(true);
            ref.setText("champ obligatoire !");
            v = false;
        }

        if (v) {
            DB db = new DB();
            PreparedStatement call1 = db.connect().prepareStatement("UPDATE bom SET \"part_id \"=?, designators=?, qty=?, comment=?, projet=?, resp=?,label=?, name=? WHERE id=?;");
            call1.setString(1, Projet.list4.get(0).getNum());
            call1.setString(2, design.getText());
            call1.setInt(3, Integer.parseInt(quantite.getText()));
            call1.setString(4, descr.getText());
            call1.setInt(5, Projet.pr);
            call1.setString(6, LoginController.name);
            call1.setString(7, mb.getText());
            call1.setString(8, part_name.getSelectionModel().getSelectedItem());
            call1.setInt(9, Projet.list4.get(0).getId());
            call1.executeUpdate();
            PreparedStatement call3 = db.connect().prepareStatement("INSERT INTO public.history(resp, event) VALUES ( ?, ?);");
            call3.setString(1, LoginController.name);
            call3.setString(2, "Mise à jour d'un composant au nomeclature du projet " + Projet.pr);
            call3.executeQuery();
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



