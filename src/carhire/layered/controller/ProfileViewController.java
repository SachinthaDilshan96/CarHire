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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class ProfileViewController {
    public Label lblName;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label lblFirstName;
    public Label lblLastName;
    public TextField txtUserID;
    public AnchorPane profileViewContext;

    UserService userService =(UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.USER);
    public void initialize(){
        UserDto userDto = UserHolder.getUserDto();
        lblName.setText(userDto.getFirstName());
        txtUserID.setText(Integer.toString(userDto.getId()));
        txtFirstName.setText(userDto.getFirstName());
        txtLastName.setText(userDto.getLastName());
        txtEmail.setText(userDto.getEmail());
    }
    public void UpdateProfileOnAction(ActionEvent actionEvent) {
            if (validateFirstName()&validateLastName()){
                try {
                    int i = userService.update(new UserDto(
                        Integer.parseInt(txtUserID.getText()),
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        txtEmail.getText(),
                            PasswordManager.encryptPassword(txtPassword.getText()),
                        UserHolder.getUserDto().getLevel(),
                        UserHolder.getUserDto().getStatus()
                    ));
                    if (i>=0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Your profile Updated");
                        alert.show();
                        txtPassword.clear();
                    }
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                    alert.show();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid user inputs");
                alert.show();
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

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) profileViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }


    private void clearFields(){
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws Exception {
        setUi("DashboardView");
    }
}
