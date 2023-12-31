package carhire.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HireSectionViewController {
    public AnchorPane homeSectionContextPane;
    public AnchorPane anchorPaneStage;
    public Button btnViewCustomers;
    public Button btnNewHire;
    public Button btnViewHires;
    public Button btnHandOver;
    public Button btnOverDueHires;

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) homeSectionContextPane.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    private void loadView(String path) throws IOException{
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/"+path+".fxml"));
        this.anchorPaneStage.getChildren().clear();
        this.anchorPaneStage.getChildren().add(root);
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException{
        setUi("DashboardView");
    }

    public void VewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnViewCustomers.setStyle("-fx-background-color: #c0392b");
        loadView("CustomerView");
    }

    public void NewHireOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnNewHire.setStyle("-fx-background-color: #c0392b");
        loadView("NewHireView");
    }

    public void ViewHiresOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnViewHires.setStyle("-fx-background-color: #c0392b");
        loadView("HiresView");
    }

    public void HandOverOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnHandOver.setStyle("-fx-background-color: #c0392b");
        loadView("ReturnVehicleView");
    }

    public void OverDueHiresOnAction(ActionEvent actionEvent) throws IOException {
        clearSelectedButtonStyle();
        btnOverDueHires.setStyle("-fx-background-color: #c0392b");
        loadView("OverdueVehiclesView");
    }

    private void clearSelectedButtonStyle(){
        btnViewCustomers.setStyle("-fx-background-color: #3498db");
        btnNewHire.setStyle("-fx-background-color: #3498db");
        btnViewHires.setStyle("-fx-background-color: #3498db");
        btnHandOver.setStyle("-fx-background-color: #3498db");
        btnOverDueHires.setStyle("-fx-background-color: #3498db");
    }
}
