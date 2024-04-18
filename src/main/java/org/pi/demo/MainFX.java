package org.pi.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pi.demo.utils.MyConnection;

import java.io.IOException;
import java.sql.SQLException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        MyConnection mc = new MyConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}