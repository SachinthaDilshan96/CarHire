package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.util.UserHolder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public Button btnHires;
    public Label lblUserName;
    public Button btnLogOut;
    public Button btnUsers;
    public Button btnAddNewUser;

    @FXML
    public void initialize(){

        showDate();
        lblUserName.setText(UserHolder.getUserDto().getFirstName()+" "+UserHolder.getUserDto().getLastName());
        btnLogOut.setText("Logout\n("+UserHolder.getUserDto().getFirstName()+")");
        if (UserHolder.getUserDto().getLevel().equalsIgnoreCase("Admin")){
            btnAddNewUser.setDisable(false);
            btnUsers.setDisable(false);
        }else{
            btnAddNewUser.setDisable(true);
            btnUsers.setDisable(true);
        }
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
        setUi("HireSectionView");
    }

    public void VehiclesOnAction(ActionEvent actionEvent) throws IOException {
        setUi("VehicleSectionView");
    }

    public void VisitProfileOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProfileView");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        UserHolder.removeCustomerDto();
        setUi("HomeView");
    }
}
