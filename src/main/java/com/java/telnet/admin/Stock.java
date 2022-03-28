package com.java.telnet.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Stock implements Initializable {

    @FXML
    private TreeView<String> tree;
    @FXML
    private Pane pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> root = new TreeItem<>("Stockage principale");
        root.setExpanded(true);
        root.getChildren().addAll(
                new TreeItem<String>("rang A"),
                new TreeItem<String>("rang B"),
                new TreeItem<String>("rang C")
        );

        tree.setRoot(root);

    }
}
