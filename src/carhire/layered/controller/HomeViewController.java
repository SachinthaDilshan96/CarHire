package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.PasswordManager;
import carhire.layered.util.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeViewController {
    public AnchorPane homeContext;
    public TextField txtEmail;
    public TextField txtPassword;
    public Button btnLogin;
    private UserService userService =(UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.USER);
    private void setUi(String url) throws IOException {
        Stage stage = (Stage) homeContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        try{
           UserDto userDto = userService.getUser(txtEmail.getText());
           if (userDto!=null){
               if (PasswordManager.validatePassword(txtPassword.getText(),userDto.getPassword())){
                   UserHolder.setUserDto(userDto);
                   setUi("DashboardView");
               }else {
                   Alert alert = new Alert(Alert.AlertType.ERROR,"Password Mismatch. Please check your password and try again");
                   alert.show();
               }
           }else{
               Alert alert = new Alert(Alert.AlertType.ERROR,"User Not Found. Please enter a valid Email");
               alert.show();
           }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR,"An Error Occurred. Please try again");
            alert.show();
        }
    }
}
