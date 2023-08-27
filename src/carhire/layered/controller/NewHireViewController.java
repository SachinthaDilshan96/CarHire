package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewHireViewController {

    public AnchorPane newHireViewContext;
    public TextField txtNic;
    public Pane panelCustomer;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtHireID;
    public Pane panelHire;
    public TextField txtPlaceBy;
    public TableView tableVehicles;
    public TextField txtVehicleID;
    public TextField txtFrom;
    public TextField txtTo;
    public TextField txtCharge;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) newHireViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) {
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void PlaceHireOnAction(ActionEvent actionEvent) {
    }

    public void AddCustomerOnAction(ActionEvent actionEvent) {
    }
}
