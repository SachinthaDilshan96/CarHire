package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.PasswordManager;
import carhire.layered.util.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddNewUserViewController {
    public RadioButton rbtnOrdinary;
    public ToggleGroup level;
    public RadioButton rbtnAdmin;
    public Button btnSaveUser;
    public Label lblEmail;
    public Label lblFirstName;
    public Label lblLastName;
    public Label lblMobile;
    public Label lblPassword;
    private UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.USER);
    public AnchorPane addNewUserContext;
    public TextField txtEmail;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtMobile;
    public TextField txtPassword;

    public void initialize(){
       // btnSaveUser.setDisable(true);
    }
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void SaveUserOnAction(ActionEvent actionEvent) {
        if (validateEmail() & validateFirstName() & validateLastName() & validateMobile()){
            try {
                  int res = userService.addUser(new UserDto(
                        0,txtFirstName.getText().substring(0,1).toUpperCase()+txtFirstName.getText().substring(1).toLowerCase(),
                        txtLastName.getText().substring(0,1).toUpperCase()+txtLastName.getText().substring(1).toLowerCase(),
                        txtEmail.getText(),
                        PasswordManager.encryptPassword(txtPassword.getText()),
                        getUserLevel(),
                          "in"));

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
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Inputs. Please Check the fields");
        }


    }
    private String getUserLevel(){
        return ((RadioButton)level.getSelectedToggle()).getText();
    }

    private boolean validateEmail(){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(txtEmail.getText()).matches()){
            lblEmail.setText("");
            txtEmail.setStyle("-fx-text-box-border: #95a5a6; -fx-focus-color: #95a5a6;-fx-border-width: 2px");
            return true;
        }else {
            txtEmail.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;-fx-border-width: 2px");
            lblEmail.setText("* (Invalid Email)");
           return false;
        }
    }

    private boolean validateFirstName(){
        String regex = "^[A-Za-z][A-Za-z]{2,15}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(txtFirstName.getText()).matches()){
            lblFirstName.setText("");
            txtFirstName.setStyle("-fx-text-box-border: #95a5a6; -fx-focus-color: #95a5a6;-fx-border-width: 2px");
            return true;
        }else {
            txtFirstName.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;-fx-border-width: 2px");
            lblFirstName.setText("* (Name should be consisted with letters only)");
            return false;
        }
    }

    private boolean validateLastName(){
        String regex = "^[A-Za-z][A-Za-z]{2,15}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(txtLastName.getText()).matches()){
            lblLastName.setText("");
            txtLastName.setStyle("-fx-text-box-border: #95a5a6; -fx-focus-color: #95a5a6;-fx-border-width: 2px");
            return true;
        }else {
            txtLastName.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;-fx-border-width: 2px");
            lblLastName.setText("* (Name should be consisted with letters only)");
            return false;
        }
    }

    private boolean validateMobile(){
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(txtMobile.getText()).matches()){
            lblMobile.setText("");
            txtMobile.setStyle("-fx-text-box-border: #95a5a6; -fx-focus-color: #95a5a6;-fx-border-width: 2px");
            return true;
        }else {
            txtMobile.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;-fx-border-width: 2px");
            lblMobile.setText("* (Mobile should be of 10 digits length)");
            return false;
        }
    }

    public void clearFields(){
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtMobile.setText("");
        txtPassword.setText("");
        rbtnOrdinary.setSelected(true);
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) addNewUserContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }
}
