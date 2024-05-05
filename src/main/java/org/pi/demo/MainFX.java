package org.pi.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.pi.demo.Exceptions.UserNotFoundException;
import org.pi.demo.controllers.UserController;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;
import org.pi.demo.services.mailing;
import org.pi.demo.utils.MyConnection;
import java.io.IOException;
import java.sql.SQLException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, UserNotFoundException {
        MyConnection mc = new MyConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user.fxml"));
        Parent root = fxmlLoader.load();
        UserController controller = fxmlLoader.getController();
        controller.start(stage);
        UserService userService = UserService.getInstance();
        SessionService sessionService = SessionService.getInstance();
        mailing mailService = new mailing();


    }

    public static void main(String[] args) {
        launch();
    }
}