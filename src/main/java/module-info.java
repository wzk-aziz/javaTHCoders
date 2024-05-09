module org.pi.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens org.pi.demo to javafx.fxml;
    exports org.pi.demo;
    exports org.pi.demo.controllers;
    opens org.pi.demo.controllers to javafx.fxml;
}