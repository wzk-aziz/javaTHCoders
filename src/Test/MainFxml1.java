package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class
MainFxml1  extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    // FXMLLoader loader=new FXMLLoader(getClass().getResource("/MenuBack.fxml"));
      FXMLLoader loader=new FXMLLoader(getClass().getResource("/ListActualite.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/ChatBot.fxml"));
        Parent root=loader.load();

        Scene scene=new Scene(root);
        stage.setTitle("Gérer Actualités");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
