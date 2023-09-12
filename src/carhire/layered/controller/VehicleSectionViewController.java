package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.util.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class VehicleSectionViewController {
    public AnchorPane vehicleSectionContext;
    public Button btnVehicleCategory;
    public Button btnVehicleBrands;
    public Button btnVehicles;
    public Button btnAvailableVehicles;
    public AnchorPane anchorPaneStage;

    @FXML
    public void initialize(){

    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }
    private void setUi(String url) throws IOException {
        Stage stage = (Stage) vehicleSectionContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void VehicleCategoryOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnVehicleCategory.setStyle("-fx-background-color: #c0392b");
        loadView("VehicleCategoryView");
    }

    public void VehicleBrandOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnVehicleBrands.setStyle("-fx-background-color: #c0392b");
        loadView("VehicleBrandView");
    }

    public void AvailableVehiclesOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnAvailableVehicles.setStyle("-fx-background-color: #c0392b");
           loadView("AvailableVehicleView");
    }

    public void VehicleOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnVehicles.setStyle("-fx-background-color: #c0392b");
          loadView("VehicleView");
    }

    private void loadView(String path) throws IOException{
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/"+path+".fxml"));
        this.anchorPaneStage.getChildren().clear();
        this.anchorPaneStage.getChildren().add(root);
    }

    private void clearSelectedButtonStyle(){
        btnAvailableVehicles.setStyle("-fx-background-color: #3498db");
        btnVehicles.setStyle("-fx-background-color: #3498db");
        btnVehicleBrands.setStyle("-fx-background-color: #3498db");
        btnVehicleCategory.setStyle("-fx-background-color: #3498db");
    }
}
