module com.java.telnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires java.desktop;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    exports com.java.telnet.admin;
    opens com.java.telnet.admin to javafx.graphics,javafx.fxml,javafx.base;
    exports com.java.telnet;
    opens com.java.telnet to javafx.graphics,javafx.fxml;
    exports com.java.telnet.admin.models;
    opens com.java.telnet.admin.models to javafx.fxml, javafx.graphics;


}

