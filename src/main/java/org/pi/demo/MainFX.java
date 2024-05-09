package org.pi.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
<<<<<<< HEAD
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pi.demo.utils.MyConnection;

=======
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
>>>>>>> origin/user
import java.io.IOException;
import java.sql.SQLException;

public class MainFX extends Application {
    @Override
<<<<<<< HEAD
    public void start(Stage stage) throws IOException, SQLException {
        MyConnection mc = new MyConnection();
     // FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("MenuBack.fxml" ));
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("Menufront.fxml" ));
        //pour menu back "MenuBack.fxml" pour menu front "MenuFront.fxml"
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
=======
    public void start(Stage stage) throws IOException, SQLException, UserNotFoundException {
        MyConnection mc = new MyConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user.fxml"));
        Parent root = fxmlLoader.load();
        UserController controller = fxmlLoader.getController();
        controller.start(stage);
        UserService userService = UserService.getInstance();
        SessionService sessionService = SessionService.getInstance();
        mailing mailService = new mailing();


>>>>>>> origin/user
    }

    public static void main(String[] args) {
        launch();
    }
}