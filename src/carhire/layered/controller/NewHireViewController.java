package carhire.layered.controller;

import carhire.layered.dto.CustomerDto;
import carhire.layered.dto.Embedded.Name;
import carhire.layered.dto.HireDto;
import carhire.layered.dto.UserDto;
import carhire.layered.dto.VehicleDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.CustomerService;
import carhire.layered.service.custom.HireService;
import carhire.layered.service.custom.VehicleService;
import carhire.layered.util.UserHolder;
import carhire.layered.view.tm.AvailableVehicleTm;
import carhire.layered.view.tm.VehicleBrandTm;
import carhire.layered.view.tm.VehicleCategoryTm;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class NewHireViewController {

    public AnchorPane newHireViewContext;
    public TextField txtNic;
    public Pane panelCustomer;
    public TextField txtFirstName;
    public TextField txtLastName;
    public Pane panelHire;
    public TextField txtPlaceBy;
    public TableView tableVehicles;
    public TextField txtVehicleID;
    public TextField txtAddress;
    public TextField txtMobile;
    public Button btnClearCustomerFields;
    public TextField txtCustomerId;
    public TextField txtDailyRental;
    public Button btnClearHireFields;
    public TextField txtTotal;
    public TextField txtDeposit;
    public TextField txtAdvance;
    public TextField txtBalance;
    public Label lblFirstName;
    public Label lblLastName;
    public Label lblMobile;
    public Label lblAddress;
    public Label lblCustomerId;
    public Label lblVehicleId;
    public Label lblPlacedBy;
    public Label lblFrom;
    public Label lblTo;
    public Label lblDailyRental;
    public Label lblTotal;
    public Label lblDeposit;
    public Label lblAdvance;
    public Label lblBalance;
    public Label lblNIC;
    public TableColumn colDailyRental;
    public DatePicker fromDate;
    public DatePicker toDate;
    public TextField txtNumberOfDays;
    public TableColumn colVehicleID;
    public TableColumn colVehicleModel;
    public TableColumn<AvailableVehicleTm,String> colType;
    public TableColumn colTransmission;
    public TableColumn colNumberOfSeats;
    public Button btnAddCustomer;

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CUSTOMER);
    VehicleService vehicleService = (VehicleService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_SERVICE);

    HireService hireService = (HireService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.HIRE);

    final int maximumRentalPeriodInDays = 30;

    public void initialize(){
        setOrderPlaceBy();
        setAvailableVehicles();
        colVehicleID.setCellValueFactory(new PropertyValueFactory<AvailableVehicleTm,Integer>("vehicleId"));
        colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleCategoryTm().getVehicleType()));
        colDailyRental.setCellValueFactory(new PropertyValueFactory("dailyRental"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<AvailableVehicleTm,String>("model"));
        colTransmission.setCellValueFactory(new PropertyValueFactory<AvailableVehicleTm,String>("transmission"));
        colNumberOfSeats.setCellValueFactory(new PropertyValueFactory<AvailableVehicleTm,Integer>("noOfSeats"));

        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());

        txtTotal.setText("0.0");
        txtNumberOfDays.setText("1");

        // add mouse event to check vehicle selection
        txtDeposit.setOnMousePressed((event -> {
            isVehicleSelected();
        }));

        //set listener to calculate the balance
        txtAdvance.setOnKeyReleased((event -> {
            calculateBalance();
        }));

        //add listener to the from date picker
        fromDate.valueProperty().addListener(
                (((observable, oldValue, newValue) -> {
                    isToDateValid(newValue);
                }))
        );
        //add a listener to the to date picker
        toDate.valueProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    txtNumberOfDays.setText(String.valueOf(ChronoUnit.DAYS.between(fromDate.getValue(),newValue)));
                })
        );

        tableVehicles.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue!=null) setData((AvailableVehicleTm)newValue);
                }));


    }

    private void setOrderPlaceBy(){
        UserDto userDto = UserHolder.getUserDto();
        if (userDto!=null){
            txtPlaceBy.setText(Integer.toString(userDto.getId()));
        }
    }

    private void setData(AvailableVehicleTm availableVehicleTm){
        txtVehicleID.setText(Integer.toString(availableVehicleTm.getVehicleId()));
        txtDailyRental.setText(Double.toString(availableVehicleTm.getDailyRental()));
        calculateTotal();
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) newHireViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) {
        if (isNICValid()){
            CustomerDto customerDto = getCustomerById();
            if (customerDto!=null){
                txtCustomerId.setText(Integer.toString(customerDto.getCustomerId()));
                txtFirstName.setText(customerDto.getName().getFirstName());
                txtLastName.setText(customerDto.getName().getLastName());
                txtAddress.setText(customerDto.getAddress());
                txtMobile.setText(customerDto.getMobileNumber());
                btnAddCustomer.setDisable(true);

            }else{
                txtCustomerId.clear();
                txtFirstName.clear();
                txtLastName.clear();
                txtAddress.clear();
                txtMobile.clear();
                new Alert(Alert.AlertType.WARNING,"Customer not found. Please add the customer first").show();
                txtNic.requestFocus();
                btnAddCustomer.setDisable(false);
            }
        }
    }

    private CustomerDto getCustomerById(){
        CustomerDto customerDto;
        try {
            customerDto = customerService.getCustomer(txtNic.getText());
        }catch (Exception e){
            customerDto =  null;
        }
        return customerDto;
    }


    private void clearCustomerFields(){
        txtNic.clear();
        txtCustomerId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtMobile.clear();
    }

    private void clearHireFields(){
        txtVehicleID.clear();
        txtDailyRental.clear();
        txtTotal.clear();
        txtDeposit.clear();
        txtAdvance.clear();
        txtBalance.clear();
        txtNumberOfDays.setText("1");
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());
    }
    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    public void PlaceHireOnAction(ActionEvent actionEvent) {
        if (txtCustomerId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please enter a customer before proceed").show();
        }else {
            if(isFromDateValid() & isToDateValid(null) & isDepositValid() & isAdvanceValid() ){
              try{
                  int i = hireService.addHire(new HireDto(
                          0,
                          Integer.parseInt(txtVehicleID.getText()),
                          Integer.parseInt(txtCustomerId.getText()),
                          Integer.parseInt(txtPlaceBy.getText()),
                          Date.valueOf(fromDate.getValue()),
                          Date.valueOf(toDate.getValue()),
                          false,
                          Double.parseDouble(txtTotal.getText()),
                          Double.parseDouble(txtDailyRental.getText()),
                          Double.parseDouble(txtDeposit.getText()),
                          Double.parseDouble(txtAdvance.getText()),
                          Double.parseDouble(txtBalance.getText())
                  ));
                  if (i>=0){
                      new Alert(Alert.AlertType.INFORMATION,"Hire placed successfully").show();
                      clearHireFields();
                      clearCustomerFields();
                  }else{
                      new Alert(Alert.AlertType.ERROR,"Hire placement failed").show();
                  }
              }catch (Exception e){
                  new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
              }
            }
        }
    }


    private void setAvailableVehicles(){
        try {
            ArrayList<VehicleDto> vehicleDtos = vehicleService.getAvailableVehicles();
            ObservableList<AvailableVehicleTm> observableList = FXCollections.observableArrayList();
            for(VehicleDto vehicleDto:vehicleDtos){
                observableList.add(new AvailableVehicleTm(
                     vehicleDto.getVehicleId(),
                     vehicleDto.getVehicleNumber(),
                     new VehicleBrandTm(vehicleDto.getVehicleBrandDto().getId(),vehicleDto.getVehicleBrandDto().getVehicleBrand()),
                     vehicleDto.getYear(),
                     vehicleDto.getModel(),
                     new VehicleCategoryTm(vehicleDto.getVehicleCategoryDto().getId(),vehicleDto.getVehicleCategoryDto().getVehicleCategory()),
                     vehicleDto.getTransmission(),
                     vehicleDto.getNoOfSeats(),
                     vehicleDto.getDailyRental(),
                     vehicleDto.getStatus()));
            }
            tableVehicles.setItems(observableList);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void AddCustomerOnAction(ActionEvent actionEvent) {
        if (isNICValid()){
            try {
                int i = customerService.addCustomer(new CustomerDto(
                        0,
                        txtNic.getText(),
                        new Name(txtFirstName.getText(),txtLastName.getText()),
                        txtAddress.getText(),
                        txtMobile.getText()
                ));
                if (i>=0){
                    new Alert(Alert.AlertType.INFORMATION,"Customer Saved Successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Customer Saving Failed").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid User Inputs.").show();
        }

    }

    private void calculateTotal(){
        if (txtVehicleID.getText() !="") {
            txtTotal.setText(Double.toString(Double.parseDouble(txtDailyRental.getText())*Integer.parseInt(txtNumberOfDays.getText())));
        }
    }

    private boolean isNICValid(){
        if (Pattern.compile("[^0-9VvWw]").matcher(txtNic.getText()).find() |
                (txtNic.getText().length()!=10)){
            lblNIC.setVisible(true);
            new Alert(Alert.AlertType.ERROR,"Invalid NIC").show();
            return false;
        }else{
            lblNIC.setVisible(false);
            return true;
        }
    }

    private boolean isFromDateValid(){
        LocalDate localDate = fromDate.getValue();
        if ( (localDate.isAfter(LocalDate.now())| localDate.isEqual(LocalDate.now()))){
            lblFrom.setVisible(false);
            return true;
        }else{
            lblFrom.setVisible(true);
            return false;
        }
    }

    private boolean isToDateValid(LocalDate maxDate){
        LocalDate localDate = toDate.getValue();
        if (maxDate==null){
            if ((localDate.isAfter(LocalDate.now())| localDate.isEqual(LocalDate.now())|localDate.isEqual(fromDate.getValue()))&localDate.isBefore(fromDate.getValue().plusDays(maximumRentalPeriodInDays+1 ))){
                lblTo.setVisible(false);
                return true;
            }else{
                lblTo.setVisible(true);
                new Alert(Alert.AlertType.WARNING,"Maximum rental period is 30 days").show();
                return false;
            }
        }else{
            if ((localDate.isAfter(LocalDate.now())| localDate.isEqual(LocalDate.now())|localDate.isEqual(fromDate.getValue()))& localDate.isBefore(maxDate)){
                lblTo.setVisible(false);
                return true;
            }else{
                lblTo.setVisible(true);
                new Alert(Alert.AlertType.WARNING,"Maximum rental period is 30 days").show();
                return false;
            }
        }
    }

    private boolean isDepositValid(){
        if (Pattern.compile("[^0-9.]").matcher(txtDeposit.getText()).find() | txtDeposit.getText().equals("")){
            lblDeposit.setVisible(true);
            return false;
        }else{
            double deposit = Double.parseDouble(txtDeposit.getText());
            if (deposit<0 | deposit>Double.parseDouble(txtTotal.getText())){
                lblDeposit.setVisible(true);
                return false;
            }else{
                lblDeposit.setVisible(false);
                return true;
            }
        }
    }
    private boolean isAdvanceValid(){
        if (Pattern.compile("[^0-9.]").matcher(txtAdvance.getText()).find()  | txtAdvance.getText().equals("")){
            lblAdvance.setVisible(true);
            return false;
        }else{
            double advance = Double.parseDouble(txtAdvance.getText());
            if (advance<0 | advance>Double.parseDouble(txtTotal.getText())){
                lblAdvance.setVisible(true);
                return false;
            }else{
                lblAdvance.setVisible(false);
                return true;
            }
        }
    }

    private void calculateBalance(){
        if (txtAdvance.getText().equals("") | !isAdvanceValid()){
            lblAdvance.setVisible(true);
        }else {
            lblAdvance.setVisible(false);
            txtBalance.setText(Double.toString(Double.parseDouble(txtTotal.getText())-Double.parseDouble(txtAdvance.getText())));
        }
    }
    public void ClearCustomerFieldsOnAction(ActionEvent actionEvent) {
        clearCustomerFields();
    }

    public void ClearHireFieldsOnAction(ActionEvent actionEvent) {
        clearHireFields();
    }


    public boolean isVehicleSelected() {
        if (txtVehicleID.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select a vehicle first").show();
            return false;
        }else{
            return true;
        }
    }
}
