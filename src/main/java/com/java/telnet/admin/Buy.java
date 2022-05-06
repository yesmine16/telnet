package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Buy implements Initializable {

    @FXML
    private TextArea descr;
    @FXML
    private TextField part_name;
    @FXML
    private FontAwesomeIconView moins;

    @FXML
    private FontAwesomeIconView plus;

    @FXML
    private TextField quantite;

    @FXML
    private Text ref;


    @FXML
    private Text txt1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ref.setVisible(false);
        txt1.setVisible(false);
        plus.setOnMouseClicked(ev -> {
            quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) + 1));
        });

        moins.setOnMouseClicked(ev -> {
            ref.setVisible(false);
            if (Integer.parseInt(quantite.getText()) != 0) {
                quantite.setText(String.valueOf(Integer.parseInt(quantite.getText()) - 1));
            }
        });
    }

    Boolean v;

    public void submit() throws SQLException {
        v = true;
        txt1.setVisible(false);
        ref.setVisible(false);

        if (part_name.getText().isEmpty()) {
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

            PreparedStatement call2 = db.connect().prepareStatement("INSERT INTO public.\"purchase_list \"( part_name, qty_needed, \"description \", resp)VALUES (?, ?, ?, ?);");
            call2.setString(1, part_name.getText());
            call2.setInt(2, Integer.parseInt(quantite.getText()));
            call2.setString(3, descr.getText());
            call2.setString(4, LoginController.name);
            call2.execute();
            call2.close();
            CallableStatement call = db.connect().prepareCall("INSERT INTO public.history(resp, event) VALUES ( ?, ?);");
            call.setString(1, LoginController.name);
            call.setString(2, "Ajout un composant au liste des achats ");
            call.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Opération réussite !");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) plus.getScene().getWindow();
                stage.close();
            }
        }
    }

}
