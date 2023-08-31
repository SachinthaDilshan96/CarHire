package carhire.layered.controller;

import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            userService.getUser(txtEmail.getText()).toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        setUi("DashboardView");
    }
}
