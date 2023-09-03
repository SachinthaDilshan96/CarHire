package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerViewController {
    public AnchorPane customerViewContext;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) customerViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) {
    }

    public void UpdateCustomerOnAction(ActionEvent actionEvent) {
    }
}
