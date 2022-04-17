package com.java.telnet.admin;

import com.google.zxing.WriterException;
import com.java.telnet.DB;

import com.java.telnet.Login;
import com.java.telnet.LoginController;
import com.java.telnet.admin.models.Get_user;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Privileges implements Initializable {


    @FXML
    private ChoiceBox<String> buy;

    @FXML
    private ToggleGroup grp;

    @FXML
    private ToggleGroup grp2;

    @FXML
    private ToggleGroup grp3;

    @FXML
    private ToggleGroup grp4;

    @FXML
    private ToggleGroup grp5;

    @FXML
    private ToggleGroup grp6;

    @FXML
    private ToggleGroup grp7;

    @FXML
    private ChoiceBox<String> hist;


    @FXML
    private ChoiceBox<String> parts;

    @FXML
    private ChoiceBox<String> projet;


    @FXML
    private ChoiceBox<String> store;

    @FXML
    private ChoiceBox<String> tableau;

    @FXML
    private CheckBox tout;

    @FXML
    private ChoiceBox<String> users;

    @FXML
    private Circle avatar;
    @FXML
    private TextField mail;

    @FXML
    private TextField mat;

    @FXML
    private TextField nom;
    @FXML
    private TextField stat, phone;

    @FXML
    private Label txt, txt1, txt3, txt4, txt5, txt6;


    @FXML
    private Button submit;
    Image img = new Image(getClass().getResource("pdp.jpg").toString());
    String[] s = Users.s;

    public void toggle() {
        if (grp.getToggles().get(0).isSelected())
            tableau.setVisible(true);
        else
            tableau.setVisible(false);
        if (grp2.getToggles().get(0).isSelected())
            users.setVisible(true);
        else
            users.setVisible(false);
        if (grp3.getToggles().get(0).isSelected())
            parts.setVisible(true);
        else
            parts.setVisible(false);
        if (grp4.getToggles().get(0).isSelected())
            hist.setVisible(true);
        else
            hist.setVisible(false);
        if (grp5.getToggles().get(0).isSelected())
            projet.setVisible(true);
        else
            projet.setVisible(false);
        if (grp6.getToggles().get(0).isSelected())
            store.setVisible(true);
        else
            store.setVisible(false);
        if (grp7.getToggles().get(0).isSelected())
            buy.setVisible(true);
        else
            buy.setVisible(false);
    }

    public void setup() {
        for (int i = 0; i < 7; i++) {
            List ls = List.of(s[i].replaceAll("[{}]", "").split(","));
            if (ls.get(0).equals("oui") && i == 0) {
                grp.getToggles().get(0).setSelected(true);
                tableau.setVisible(true);
                if (ls.get(1).equals("oui") && i == 0) {
                    tableau.getSelectionModel().selectLast();
                } else {
                    tableau.getSelectionModel().selectFirst();
                }

            } else if (ls.get(0).equals("non") && i == 0) grp.getToggles().get(1).setSelected(true);

            if (ls.get(0).equals("oui") && i == 3) {
                grp5.getToggles().get(0).setSelected(true);
                projet.setVisible(true);
                if (ls.get(1).equals("oui") && i == 3) {
                    projet.getSelectionModel().selectLast();
                } else projet.getSelectionModel().selectFirst();
            } else if (ls.get(0).equals("non") && i == 3) grp5.getToggles().get(1).setSelected(true);

            if (ls.get(0).equals("oui") && i == 1) {
                grp2.getToggles().get(0).setSelected(true);
                users.setVisible(true);
                if (ls.get(1).equals("oui") && i == 1)
                    users.getSelectionModel().selectLast();
                else users.getSelectionModel().selectFirst();

            } else if (ls.get(0).equals("non") && i == 1) grp2.getToggles().get(1).setSelected(true);

            if (ls.get(0).equals("oui") && i == 2) {
                grp3.getToggles().get(0).setSelected(true);
                parts.setVisible(true);
                if (ls.get(1).equals("oui") && i == 2)
                    parts.getSelectionModel().selectLast();
                else parts.getSelectionModel().selectFirst();

            } else if (ls.get(0).equals("non") && i == 2) grp3.getToggles().get(1).setSelected(true);
            if (ls.get(0).equals("oui") && i == 4) {
                grp6.getToggles().get(0).setSelected(true);
                store.setVisible(true);
                if (ls.get(1).equals("oui") && i == 4)
                    store.getSelectionModel().selectLast();
                else store.getSelectionModel().selectFirst();

            } else if (ls.get(0).equals("non") && i == 4) grp6.getToggles().get(1).setSelected(true);

            if (ls.get(0).equals("oui") && i == 6) {

                grp7.getToggles().get(0).setSelected(true);
                buy.setVisible(true);
                if (ls.get(1).equals("oui") && i == 6)
                    buy.getSelectionModel().selectLast();
                else buy.getSelectionModel().selectFirst();

            } else if (ls.get(0).equals("non") && i == 6) grp7.getToggles().get(1).setSelected(true);

            if (ls.get(0).equals("oui") && i == 5) {

                grp4.getToggles().get(0).setSelected(true);
                hist.setVisible(true);
                if (ls.get(1).equals("oui") && i == 5)
                    hist.getSelectionModel().selectLast();
                else hist.getSelectionModel().selectFirst();

            } else if (ls.get(0).equals("non") && i == 5) grp4.getToggles().get(1).setSelected(true);
        }
    }

    public void select() {
        if (tout.isSelected()) {
            grp.getToggles().get(0).setSelected(true);
            grp2.getToggles().get(0).setSelected(true);
            grp3.getToggles().get(0).setSelected(true);
            grp4.getToggles().get(0).setSelected(true);
            grp5.getToggles().get(0).setSelected(true);
            grp6.getToggles().get(0).setSelected(true);
            grp7.getToggles().get(0).setSelected(true);
            users.setVisible(true);
            tableau.setVisible(true);
            hist.setVisible(true);
            buy.setVisible(true);
            store.setVisible(true);
            projet.setVisible(true);
            parts.setVisible(true);
        } else {
            grp.getToggles().get(0).setSelected(false);
            grp2.getToggles().get(0).setSelected(false);
            grp3.getToggles().get(0).setSelected(false);
            grp4.getToggles().get(0).setSelected(false);
            grp5.getToggles().get(0).setSelected(false);
            grp6.getToggles().get(0).setSelected(false);
            grp7.getToggles().get(0).setSelected(false);
            users.setVisible(false);
            tableau.setVisible(false);
            hist.setVisible(false);
            buy.setVisible(false);
            store.setVisible(false);
            projet.setVisible(false);
            parts.setVisible(false);
        }

    }

    String num = "\\d+";
    Boolean verif = true;
    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mail.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!mail.getText().matches(EMAIL_PATTERN)) {

                    txt4.setText("Format non convenable");
                    txt4.setVisible(true);
                    txt4.setManaged(true);
                }
            } else {
                txt4.setVisible(false);
                txt4.setManaged(false);
                txt4.setText("Email est obligatoire");


            }

        });
        phone.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!phone.getText().matches(num)) {

                    txt5.setText("Uniquement des chiffres");
                    txt5.setVisible(true);
                    txt5.setManaged(true);
                }
            } else {
                txt5.setVisible(false);
                txt5.setManaged(false);
                txt5.setText("Numéro téléphone est obligatoire");


            }

        });
        mat.focusedProperty().addListener((observableValue, old, newval) -> {
            if (old) {
                DB db = new DB();
                try {
                    PreparedStatement ps = db.connect().prepareStatement("select matricule from login_info where matricule=?");
                    ps.setString(1, mat.getText());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        txt.setVisible(true);
                        txt.setManaged(true);
                        txt.setText("Matricule existe déjà");
                        verif = false;

                    } else {
                        txt.setVisible(false);
                        txt.setManaged(false);
                        txt.setText("Matricule est obligatoire");
                    }
                } catch (Exception e) {
                }
            }

        });

        txt.setManaged(false);
        txt1.setManaged(false);
        txt3.setManaged(false);
        txt4.setManaged(false);
        txt5.setManaged(false);

        users.getItems().addAll("Lecture", "Lecture et Ecriture");
        users.getSelectionModel().selectFirst();
        tableau.getItems().addAll("Lecture", "Lecture et Ecriture");
        tableau.getSelectionModel().selectFirst();

        hist.getItems().addAll("Lecture", "Lecture et Ecriture");
        hist.getSelectionModel().selectFirst();

        buy.getItems().addAll("Lecture", "Lecture et Ecriture");
        buy.getSelectionModel().selectFirst();

        store.getItems().addAll("Lecture", "Lecture et Ecriture");
        store.getSelectionModel().selectFirst();

        projet.getItems().addAll("Lecture", "Lecture et Ecriture");
        projet.getSelectionModel().selectFirst();

        parts.getItems().addAll("Lecture", "Lecture et Ecriture");
        parts.getSelectionModel().selectFirst();
        submit.setOnMouseClicked(event -> {
            if (Users.list2.isEmpty()) {
                try {
                    insert();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    update();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        if (Users.list2.isEmpty() == false) {
            try {
                load();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mat.setText(Users.list2.get(0).getMatricule());
            phone.setText(Users.list2.get(0).getPhone());
            mail.setText(Users.list2.get(0).getEmail());
            nom.setText(Users.list2.get(0).getNom());
            stat.setText(Users.list2.get(0).getUser());
            avatar.setFill(new ImagePattern(Users.list2.get(0).getPhoto().getImage()));
            setup();


        } else {
            avatar.setFill(new ImagePattern(img));

        }

    }

    public File file1 = null;

    public void img() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            avatar.setFill(new ImagePattern(image1));
            file1 = new File(file.toURI().getPath());
        }
    }

    Boolean v;

    public void insert() throws SQLException, IOException, WriterException {
        v = true;
        if (!mail.getText().matches(EMAIL_PATTERN)) {
            txt4.setText("Format non convenable");
            txt4.setVisible(true);
            txt4.setManaged(true);
            v = false;
        }
        if (!phone.getText().matches(num)) {

            txt5.setText("Uniquement des chiffres");
            txt5.setVisible(true);
            txt5.setManaged(true);
            v = false;
        }
        if (grp.getSelectedToggle() == null || grp2.getSelectedToggle() == null || grp3.getSelectedToggle() == null || grp4.getSelectedToggle() == null || grp5.getSelectedToggle() == null || grp6.getSelectedToggle() == null || grp7.getSelectedToggle() == null) {
            txt6.setVisible(true);
            v = false;
        }
        if (mat.getText().isEmpty()) {
            txt.setVisible(true);
            txt.setManaged(true);
            txt.setText("Matricule est obligatoire");
            v = false;

        }
        if (nom.getText().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }
        if (mail.getText().isEmpty()) {
            txt4.setVisible(true);
            txt4.setManaged(true);
            txt4.setText("Email est obligatoire");
            v = false;

        }
        if (phone.getText().isEmpty()) {
            txt5.setVisible(true);
            txt5.setManaged(true);
            txt5.setText("Numéro téléphone est obligatoire");
            v = false;
        }
        if (stat.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;

        }

        if (verif == false) {
            txt.setText("Matricule existe déjà");
            txt.setVisible(true);
            txt.setManaged(true);
            v = false;
        }
        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("INSERT INTO login_info (matricule, \"user\", photo, nom, email, phone,qr) VALUES ( ?,?,?,?,?,?,?);");
            PreparedStatement insert = db.connect().prepareStatement("INSERT into privilege(\"table\", users, parts, projects, storage, history, buy)VALUES (?,?,?,?,?,?,?);");
            ps.setString(4, nom.getText());
            ps.setString(1, mat.getText());
            ps.setString(2, stat.getText());
            ps.setString(5, mail.getText());
            ps.setString(6, phone.getText());
            ps.setBytes(7, Qr.getQRCodeImage(mat.getText(), 250, 250));

            if (file1 != null) {
                FileInputStream fin = new FileInputStream(file1);
                ps.setBinaryStream(3, fin, (int) file1.length());
            } else {
                File f = new File("src/main/resources/com/java/telnet/admin/pdp.jpg");
                FileInputStream fin = new FileInputStream(f);
                ps.setBinaryStream(3, fin, (int) f.length());
            }
            if (grp.getToggles().get(0).isSelected()) {
                if (tableau.getSelectionModel().isSelected(0)) {
                    insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp2.getToggles().get(0).isSelected()) {
                if (users.getSelectionModel().isSelected(0)) {
                    insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp3.getToggles().get(0).isSelected()) {
                if (parts.getSelectionModel().isSelected(0)) {
                    insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp4.getToggles().get(0).isSelected()) {
                if (hist.getSelectionModel().isSelected(0)) {
                    insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp5.getToggles().get(0).isSelected()) {
                if (projet.getSelectionModel().isSelected(0)) {
                    insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp6.getToggles().get(0).isSelected()) {
                if (store.getSelectionModel().isSelected(0)) {
                    insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp7.getToggles().get(0).isSelected()) {
                if (buy.getSelectionModel().isSelected(0)) {
                    insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            ps.executeUpdate();
            insert.executeUpdate();
            insert.close();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'utilisateur " + nom.getText() + " a été ajouté avec succes");
            alert.show();
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();


            }
        }

    }

    public void update() throws SQLException, IOException {
        v = true;
        if (!mail.getText().matches(EMAIL_PATTERN)) {

            txt4.setText("Format non convenable");
            txt4.setVisible(true);
            txt4.setManaged(true);
            v = false;
        }
        if (!phone.getText().matches(num)) {

            txt5.setText("Uniquement des chiffres");
            txt5.setVisible(true);
            txt5.setManaged(true);
            v = false;
        }
        if (grp.getSelectedToggle() == null || grp2.getSelectedToggle() == null || grp3.getSelectedToggle() == null || grp4.getSelectedToggle() == null || grp5.getSelectedToggle() == null || grp6.getSelectedToggle() == null || grp7.getSelectedToggle() == null) {
            txt6.setVisible(true);
            v = false;
        }
        if (mat.getText().isEmpty()) {
            txt.setVisible(true);
            txt.setManaged(true);
            v = false;
        }
        if (nom.getText().isEmpty()) {
            txt1.setVisible(true);
            txt1.setManaged(true);
            v = false;
        }
        if (mail.getText().isEmpty()) {
            txt4.setVisible(true);
            txt4.setManaged(true);
            v = false;
        }
        if (phone.getText().isEmpty()) {
            txt5.setVisible(true);
            txt5.setManaged(true);
            v = false;
        }
        if (stat.getText().isEmpty()) {
            txt3.setVisible(true);
            txt3.setManaged(true);
            v = false;
        }

        if (verif == false) {
            txt.setText("Matricule existe déjà");
            txt.setVisible(true);
            txt.setManaged(true);
            v = false;
        }
        if (v) {
            DB db = new DB();
            PreparedStatement ps = db.connect().prepareStatement("UPDATE login_info SET  matricule=?, \"user\"=?, photo=?, nom=?, email=?, phone=? WHERE matricule=? ");
            PreparedStatement insert = db.connect().prepareStatement("UPDATE privilege SET \"table\"=?, users=?, parts=?, projects=?, storage=?, history=?, buy=? WHERE id=?;");
            ps.setString(4, nom.getText());
            ps.setString(1, mat.getText());
            ps.setString(2, stat.getText());
            ps.setString(5, mail.getText());
            ps.setString(6, phone.getText());
            if (file1 != null) {
                FileInputStream fin = new FileInputStream(file1);
                ps.setBinaryStream(3, fin, (int) file1.length());
            } else {

                File f = new File("src/main/resources/com/java/telnet/admin/photo.jpg");
                FileInputStream fin = new FileInputStream(f);
                ps.setBinaryStream(3, fin, (int) f.length());
            }
            ps.setString(7, Users.list2.get(0).getMatricule());
            insert.setInt(8, Users.list2.get(0).getId());
            if (grp.getToggles().get(0).isSelected()) {
                if (tableau.getSelectionModel().isSelected(0)) {
                    insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(1, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp2.getToggles().get(0).isSelected()) {
                if (users.getSelectionModel().isSelected(0)) {
                    insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(2, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp3.getToggles().get(0).isSelected()) {
                if (parts.getSelectionModel().isSelected(0)) {
                    insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(3, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp4.getToggles().get(0).isSelected()) {
                if (hist.getSelectionModel().isSelected(0)) {
                    insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(4, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp5.getToggles().get(0).isSelected()) {
                if (projet.getSelectionModel().isSelected(0)) {
                    insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(5, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp6.getToggles().get(0).isSelected()) {
                if (store.getSelectionModel().isSelected(0)) {
                    insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(6, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            if (grp7.getToggles().get(0).isSelected()) {
                if (buy.getSelectionModel().isSelected(0)) {
                    insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "non"}));
                } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"oui", "oui"}));
            } else insert.setArray(7, db.connect().createArrayOf("varchar", new Object[]{"non", "non"}));
            ps.executeUpdate();
            insert.executeUpdate();
            insert.close();
            ps.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'utilisateur  a été mis à jour avec succes");
            alert.show();
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void load() throws IOException, SQLException {
        DB db = new DB();

        PreparedStatement ps = db.connect().prepareStatement("select photo from login_info where id=?");
        ps.setInt(1, Users.list2.get(0).getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            InputStream is = rs.getBinaryStream("photo");
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

}
