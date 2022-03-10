module com.java.telnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;

    exports com.java.telnet.admin;
    opens com.java.telnet.admin to javafx.graphics,javafx.fxml;
    exports com.java.telnet;
    opens com.java.telnet to javafx.graphics,javafx.fxml;



}

