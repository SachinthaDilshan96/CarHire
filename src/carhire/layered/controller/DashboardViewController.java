package carhire.layered.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardViewController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane dashboardContext;

    @FXML
    public void initialize(){
        showDate();
    }

    public void showDate(){
        Thread timeThread = new Thread(()->{
            SimpleDateFormat datedf = new SimpleDateFormat("dd MMMM yyy");
            SimpleDateFormat timedf = new SimpleDateFormat("hh:mm:ss");
            while (true){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println("An Error Occurred");
                }
                final String time = timedf.format(new Date());
                final String date = datedf.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(time);
                    lblDate.setText(date);
                });
            }
        });
        timeThread.start();
    }

    public void AddNewUserOnAction(ActionEvent actionEvent) throws IOException {
       setUi("AddNewUserView");
    }

    public void UsersOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UserView");
    }

    public void CustomersOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerView");
    }

    public void HiresOnAction(ActionEvent actionEvent) throws IOException {
        setUi("HiresView");
    }

    public void NewHireOnAction(ActionEvent actionEvent) throws IOException {
        setUi("NewHireView");
    }

    public void ReturnVehicleOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ReturnVehicleView");
    }

    public void AddNewVehicleOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddNewVehicleView");
    }

    public void VehiclesOnAction(ActionEvent actionEvent) throws IOException {
        setUi("VehicleView");
    }

    public void AvailableVehiclesOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AvailableVehicleView");
    }

    public void OverdueVehiclesOnAction(ActionEvent actionEvent) throws IOException {
        setUi("OverdueVehiclesView");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }
}
