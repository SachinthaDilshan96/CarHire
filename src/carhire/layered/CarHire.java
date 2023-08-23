package carhire.layered;

import carhire.layered.controller.HomeViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarHire extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/HomeView.fxml"))));
        primaryStage.setTitle("CarHire-Home");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
