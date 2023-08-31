package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewUserViewController {
    private UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.USER);
    public AnchorPane addNewUserContext;
    public TextField txtEmail;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtMobile;
    public TextField txtPassword;

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void SaveUserOnAction(ActionEvent actionEvent) {
        try {
           int res = userService.addUser(new UserDto(
                   0,txtFirstName.getText(),
                   txtLastName.getText(),
                   txtEmail.getText(),
                   PasswordManager.encryptPassword(txtPassword.getText())));


           if (res>=0){
               Alert alert = new Alert(Alert.AlertType.INFORMATION,"User Saved Successfully");
               alert.show();
               clearFields();
           }else{
               Alert alert = new Alert(Alert.AlertType.ERROR,"User Insertion Failed");
               alert.show();
           }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.show();
        }

    }

    public void clearFields(){
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtMobile.setText("");
        txtPassword.setText("");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) addNewUserContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }
}
