package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.UserService;
import carhire.layered.view.tm.UserTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserViewController {
    public AnchorPane userViewContext;
    public TableView tblUsers;
    public TextField txtEmail;
    public TextField txtUserID;
    public TextField txtFirstName;
    public TextField txtLastName;
    public RadioButton rbtnAdmin;
    public RadioButton rbtnOrdinary;
    public Label lblFirstName;
    public Label lblLastName;
    public ToggleGroup level;
    public Label lblStatus;
    public Button btnDelete;
    public TableColumn colID;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colEmail;
    public TableColumn colLevel;
    public TableColumn colStatus;
    private UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.USER);

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<UserTm,String>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<UserTm,String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<UserTm,String>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<UserTm,String>("email"));
        colLevel.setCellValueFactory(new PropertyValueFactory<UserTm,String>("level"));
        colStatus.setCellValueFactory(new PropertyValueFactory<UserTm,String>("status"));
        loadAllUsers();
        tblUsers.getSelectionModel().selectedItemProperty()
                .addListener((observable,oldValue,newValue)->{
                    if (newValue!=null){
                        setData((UserTm) newValue);
                    }
                });
    }
    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    private void setData(UserTm userTm){
        txtUserID.setText(Integer.toString(userTm.getId()));
        txtFirstName.setText(userTm.getFirstName());
        txtLastName.setText(userTm.getLastName());
        txtEmail.setText(userTm.getEmail());
        if (userTm.getLevel().equalsIgnoreCase("Admin")){
            rbtnAdmin.setSelected(true);
        }else{
            rbtnOrdinary.setSelected(true);
        }
        if(userTm.getStatus().equalsIgnoreCase("in")){
            lblStatus.setText("A Current User");
            btnDelete.setText("Delete");
        }else{
            lblStatus.setText("A Former User");
            btnDelete.setText("Make In");
        }

    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) userViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void SearchUserOnAction(ActionEvent actionEvent) {
        try {
            UserDto userDto = userService.getUser(txtEmail.getText(),true);
            if (userDto!=null){
                txtUserID.setText(Integer.toString(userDto.getId()));
                txtFirstName.setText(userDto.getFirstName());
                txtLastName.setText(userDto.getLastName());
                if (userDto.getLevel().equalsIgnoreCase("Admin")){
                    rbtnAdmin.setSelected(true);
                }else{
                    rbtnOrdinary.setSelected(true);
                }
                if(userDto.getStatus().equalsIgnoreCase("in")){
                    lblStatus.setText("A Current User");
                }else{
                    lblStatus.setText("A Former User");
                    btnDelete.setText("Make In");

                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING,"User Not Found");
                alert.show();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.show();
        }
    }

    public void loadAllUsers(){
        ObservableList<UserTm> observableList = FXCollections.observableArrayList();
       try {
           ArrayList<UserDto> allusers = userService.getAllUsers();
           for (UserDto user:allusers) {
               observableList.add(new UserTm(
                       user.getId(),
                       user.getFirstName(),
                       user.getLastName(),
                       user.getEmail(),
                       user.getLevel(),
                       user.getStatus()));
           }

       }catch (Exception e){
           Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
           alert.show();
       }
        tblUsers.setItems(observableList);
    }

    public void UpdateUserOnAction(ActionEvent actionEvent) {
        updateUser(null);
    }

    private void updateUser(String...status){
        if (validateFirstName() & validateLastName()){
            String s;
            if (status==null){
                if(btnDelete.getText().equalsIgnoreCase("Make In")){
                    s = "out";
                }else{
                    s = "in";
                }
            }else{
                s = "in";
            }
            UserDto userDto = new UserDto(
                    Integer.parseInt(txtUserID.getText()),
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    "",((RadioButton)level.getSelectedToggle()).getText(),
                    s);
            try {
                if (userService.update(userDto)>=0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            (status==null)?"User updated successfully":"User Made In successfully");
                    loadAllUsers();
                    alert.show();
                    clearFields();
                    lblStatus.setText("");
                    btnDelete.setText("Delete");
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"User couldn't be updated. Please try again");
                    alert.show();
                    loadAllUsers();
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Inputs. Please Check and try again");
            alert.show();
        }
    }

    public void DeleteUserOnAction(ActionEvent actionEvent) {
        if (btnDelete.getText().equalsIgnoreCase("Make In")){
              updateUser("in");
        }else{
            try {
                int i = userService.deleteUser(Integer.parseInt(txtUserID.getText()));
                if (i>=0){
                    loadAllUsers();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"User Deleted Successfully");
                    alert.show();
                    clearFields();
                    lblStatus.setText("");
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"User Deletion failed. Please try again");
                    alert.show();
                    loadAllUsers();
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
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

    private void clearFields(){
        txtEmail.clear();
        txtUserID.clear();
        txtFirstName.clear();
        txtLastName.clear();
    }
}
