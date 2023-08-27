package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HiresViewController {
    public AnchorPane hiresViewContext;
    public TextField txtCustomerName;
    public TextField txtStatus;
    public TableView tblHires;
    public TextField txtCharge;
    public TextField txtHirePlacedBy;
    public TextField txtVehicleNumber;
    public TextField txtTo;
    public TextField txtFrom;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) hiresViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }
}
