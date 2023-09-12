package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReturnVehicleViewController {

    public AnchorPane returnVehicleViewContext;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) returnVehicleViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void SearchHireOnAction(ActionEvent actionEvent) {
    }
}
