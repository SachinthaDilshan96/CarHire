package carhire.layered.controller;

import carhire.layered.dto.CustomerDto;
import carhire.layered.dto.Embedded.Name;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.CustomerService;
import carhire.layered.view.tm.AvailableVehicleTm;
import carhire.layered.view.tm.CustomerTm;
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

public class CustomerViewController {
    public AnchorPane customerViewContext;
    public Button btnSearch;
    public Button btnUpdate;
    public TableView tblCustomer;
    public TableColumn colID;
    public TableColumn colNic;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colAddress;
    public TableColumn colMobile;
    public Label lblNIC;
    public Label lblCustomerId;
    public Label lblFirstName;
    public Label lblLastName;
    public Label lblAddress;
    public Label lblMobile;
    public TextField txtNIC;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAddress;
    public TextField txtMobile;
    public TextField txtCustomerId;

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CUSTOMER);
    public void initialize(){
        loadAllCustomers();
        colID.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("customerId"));
        colNic.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("customerNic"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<CustomerTm,Integer>("mobile"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) ->{
                    if (newValue!=null) setData((CustomerTm)newValue);
                } ));
    }
    private void setUi(String url) throws IOException {
        Stage stage = (Stage) customerViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }
    private void setData(CustomerTm customerTm){
        txtCustomerId.setText(Integer.toString(customerTm.getCustomerId()));
        txtNIC.setText(customerTm.getCustomerNic());
        txtFirstName.setText(customerTm.getFirstName());
        txtLastName.setText(customerTm.getLastName());
        txtAddress.setText(customerTm.getAddress());
        txtMobile.setText(customerTm.getMobile());
    }

    private void loadAllCustomers(){
        try {
            ArrayList<CustomerDto> customerDtos = customerService.getAllCustomers();
            ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList();
            for (CustomerDto customerDto:customerDtos){
                customerTms.add(new CustomerTm(
                        customerDto.getCustomerId(),
                        customerDto.getNic(),
                        customerDto.getName().getFirstName(),
                        customerDto.getName().getLastName(),
                        customerDto.getAddress(),
                        customerDto.getMobileNumber()
                ));
                tblCustomer.setItems(customerTms);
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }
    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) {
        if (isNICValid()){
            try{
                CustomerDto customerDto = customerService.getCustomer(txtNIC.getText());
                if (customerDto!=null){
                    txtCustomerId.setText(Integer.toString(customerDto.getCustomerId()));
                    txtFirstName.setText(customerDto.getName().getFirstName());
                    txtLastName.setText(customerDto.getName().getLastName());
                    txtAddress.setText(customerDto.getAddress());
                    txtMobile.setText(customerDto.getMobileNumber());
                }else{
                    new Alert(Alert.AlertType.WARNING,"Customer Not Found.").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    public void UpdateCustomerOnAction(ActionEvent actionEvent) {
        if (txtCustomerId.getText().equals("")){
            new Alert(Alert.AlertType.ERROR,"Please select a customer to update").show();
        }else {
            if (isFirstNameValid() & isLastNameValid() & isMobileValid()){
                try{
                    int i = customerService.updateCustomer(new CustomerDto(
                            Integer.parseInt(txtCustomerId.getText()),
                            txtNIC.getText(),
                            new Name(txtFirstName.getText(), txtLastName.getText()),
                            txtAddress.getText(),
                            txtMobile.getText()
                    ));
                    if (i>=0){
                        new Alert(Alert.AlertType.INFORMATION,"Customer updated successfully").show();
                        clearFields();
                        loadAllCustomers();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Customer update failed").show();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR,e.getMessage());
                }
            }
        }
    }

    private boolean isNICValid(){
        if (Pattern.compile("[^0-9VvWw]").matcher(txtNIC.getText()).find() |
                (txtNIC.getText().length()!=10)){
            lblNIC.setVisible(true);
            new Alert(Alert.AlertType.ERROR,"Invalid NIC").show();
            return false;
        }else{
            lblNIC.setVisible(false);
            return true;
        }
    }

    private boolean isFirstNameValid(){
        if (Pattern.compile("[^A-Za-z]").matcher(txtFirstName.getText()).find()){
            lblFirstName.setVisible(true);
            return false;
        }else{
            lblFirstName.setVisible(false);
            return true;
        }
    }

    private boolean isLastNameValid(){
        if (Pattern.compile("[^A-Za-z]").matcher(txtLastName.getText()).find() ){
            lblLastName.setVisible(true);
            return false;
        }else{
            lblLastName.setVisible(false);
            return true;
        }
    }

    private boolean isMobileValid(){
        if (Pattern.compile("[^0-9]").matcher(txtMobile.getText()).find() |
                (txtMobile.getText().length()!=10)){
            lblMobile.setVisible(true);
            return false;
        }else{
            lblMobile.setVisible(false);
            return true;
        }
    }

    private void clearFields(){
        txtNIC.clear();
        txtCustomerId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtMobile.clear();
    }
}
