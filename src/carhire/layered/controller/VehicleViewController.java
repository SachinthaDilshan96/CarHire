package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class VehicleViewController {
    public AnchorPane vehicleViewContext;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) vehicleViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void UpdateVehicleOnAction(ActionEvent actionEvent) {
    }

    public void DeleteVehicleOnAction(ActionEvent actionEvent) {
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) {
    }
}
