module org.pi.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.swing;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires twilio;

    opens org.pi.demo to javafx.fxml;
    exports org.pi.demo;
    exports org.pi.demo.controllers;
    opens org.pi.demo.controllers to javafx.fxml;
}