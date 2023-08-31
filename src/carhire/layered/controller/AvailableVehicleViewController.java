package carhire.layered.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AvailableVehicleViewController {
    public AnchorPane availableVehicleViewContext;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) availableVehicleViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }
}
