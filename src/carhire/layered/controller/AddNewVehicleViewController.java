package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewVehicleViewController {
    public AnchorPane addNewVehicleContext;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) addNewVehicleContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboadView");
    }

    public void AddVehicleOnAction(ActionEvent actionEvent) {
    }
}
