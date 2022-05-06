package com.java.telnet.admin;

import com.java.telnet.DB;
import com.java.telnet.LoginController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.PopOver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Default implements Initializable {
@FXML
private Rectangle avatar;
    @FXML
    private ImageView img;

    @FXML
    private Label mail;

    @FXML
    private Label mat;

    @FXML
    private Label name;


    @FXML
    private Label stat;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
        try {
            PreparedStatement ps = db.connect().prepareStatement("select * from login_info where id=?");
            ps.setInt(1, LoginController.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                InputStream is = rs.getBinaryStream("photo");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image imagex = new Image("file:photo.jpg", 250, 250, true, true);
                avatar.setFill(new ImagePattern(imagex));

                stat.setText(rs.getString(3));
                name.setText(rs.getString(6));
                mat.setText(rs.getString(2));
                mail.setText(rs.getString(7));


            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
