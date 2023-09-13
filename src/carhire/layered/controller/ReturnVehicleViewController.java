package carhire.layered.controller;

import carhire.layered.dto.HireDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.HireService;
import carhire.layered.view.tm.CustomerTm;
import carhire.layered.view.tm.HireTm;
import carhire.layered.view.tm.UserTm;
import carhire.layered.view.tm.VehicleTm;
import javafx.beans.property.SimpleStringProperty;
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
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReturnVehicleViewController {

    public AnchorPane returnVehicleViewContext;
    public TextField txtHireIdSearch;
    public TextField txtCustomerName;
    public TextField txtFrom;
    public TextField txtTo;
    public TextField txtISReturned;
    public TextField txtHirePlacedBy;
    public TextField txtTotal;
    public TableView tblHires;
    public TableColumn colHireId;
    public TableColumn<HireTm,String> colVehicle;
    public TableColumn<HireTm,String> colCustomer;
    public TableColumn colFrom;
    public TableColumn colTo;
    public TableColumn colTotal;
    public TableColumn colIsReturned;
    public TableColumn colBalance;
    public TextField txtDailyRental;
    public TextField txtVehicle;
    public TextField txtDeposit;
    public TextField txtAdvance;
    public TextField txtBalance;
    public Label txtVehicleId;
    public TextField txtHireID;
    public Button btnMarkAsReturned;

    HireService hireService = (HireService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.HIRE);
    public void initialize(){
        colHireId.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("hireId"));
        colVehicle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicle().getVehicleNumber()));
        colCustomer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName()));
        colFrom.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("fromDate"));
        colTo.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("toDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("total"));
        colIsReturned.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("isReturned"));
        colBalance.setCellValueFactory(new PropertyValueFactory<HireTm,Integer>("balance"));

        loadAllHires();

        tblHires.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) ->{
                    if (newValue!=null)setData((HireTm) newValue);
                } ));
    }
    private void setData(HireTm hireTm){
        txtHireID.setText(Integer.toString(hireTm.getHireId()));
        txtVehicle.setText(hireTm.getVehicle().getVehicleNumber());
        txtCustomerName.setText(hireTm.getCustomer().getFirstName());
        txtHirePlacedBy.setText(hireTm.getOrderPlacedBy().getFirstName());
        txtFrom.setText(hireTm.getFromDate().toString());
        if (hireTm.getToDate().before(Date.valueOf(LocalDate.now())) ) {
            txtTo.setStyle("-fx-border-color: #e67e22;-fx-border-width: 2");
            new Alert(Alert.AlertType.WARNING,"This is an overdue vehicle. Charge the delay fees.").show();
            int days = (int) ChronoUnit.DAYS.between(hireTm.getFromDate().toLocalDate(),LocalDate.now());
            double total = hireTm.getDailyRental()*days;
            txtTotal.setText(Double.toString(total));
            txtBalance.setText(Double.toString(hireTm.getBalance()-hireTm.getAdvance()));

        }else {
            txtTo.setStyle("-fx-border-width: 0");
            txtTotal.setText(Double.toString(hireTm.getTotal()));
            txtBalance.setText(Double.toString(hireTm.getBalance()));
        }
        txtTo.setText(hireTm.getToDate().toString());
        txtISReturned.setText(hireTm.getIsReturned());
        txtDailyRental.setText(Double.toString(hireTm.getDailyRental()));
        txtDeposit.setText(Double.toString(hireTm.getDeposit()));
        txtAdvance.setText(Double.toString(hireTm.getAdvance()));

    }

    private void loadAllHires(){
        ObservableList<HireTm> hireTms = null;
        try{
            ArrayList<HireDto> hireDtos = hireService.getAllHiresToBeReturned();
            hireTms = FXCollections.observableArrayList();
            if (hireDtos.size()>0){
                for (HireDto hireDto:hireDtos){
                    VehicleTm vehicleTm = new VehicleTm();
                    vehicleTm.setVehicleId(hireDto.getVehicle().getVehicleId());
                    vehicleTm.setVehicleNumber(hireDto.getVehicle().getVehicleNumber());

                    CustomerTm customerTm = new CustomerTm();
                    customerTm.setCustomerId(hireDto.getCustomer().getCustomerId());
                    customerTm.setFirstName(hireDto.getCustomer().getName().getFirstName());

                    UserTm userTm = new UserTm();
                    userTm.setId(hireDto.getOrderPlacedBy().getId());
                    userTm.setFirstName(hireDto.getOrderPlacedBy().getFirstName());

                    hireTms.add(new HireTm(
                            hireDto.getHireId(),
                            vehicleTm,
                            customerTm,
                            userTm,
                            hireDto.getFromDate(),
                            hireDto.getToDate(),
                            hireDto.getIsReturned(),
                            hireDto.getTotal(),
                            hireDto.getDailyRental(),
                            hireDto.getDeposit(),
                            hireDto.getAdvance(),
                            hireDto.getBalance()
                    ));
                }
            }
            tblHires.setItems(hireTms);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) returnVehicleViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void SearchHireOnAction(ActionEvent actionEvent) {
        if (txtHireIdSearch.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Enter a valid Hire ID").show();
        }else{
            HireDto hireDto = getHireDtoById();
            if(hireDto!=null){
                txtHireID.setText(Integer.toString(hireDto.getHireId()));
                txtVehicle.setText(hireDto.getVehicle().getVehicleNumber());
                txtCustomerName.setText(hireDto.getCustomer().getName().getFirstName());
                txtHirePlacedBy.setText(hireDto.getOrderPlacedBy().getFirstName());
                txtFrom.setText(hireDto.getFromDate().toString());
                txtTo.setText(hireDto.getToDate().toString());
                txtISReturned.setText(hireDto.getIsReturned());
                txtTotal.setText(Double.toString(hireDto.getTotal()));
                txtDailyRental.setText(Double.toString(hireDto.getDailyRental()));
                txtDeposit.setText(Double.toString(hireDto.getDeposit()));
                txtAdvance.setText(Double.toString(hireDto.getAdvance()));
                txtBalance.setText(Double.toString(hireDto.getBalance()));
            }else{
                new Alert(Alert.AlertType.WARNING,"Invalid Hire ID").show();
            }
        }
    }
    private HireDto getHireDtoById(){
        HireDto hireDto = null;
        try {
            hireDto = hireService.getHire(Integer.parseInt(txtHireIdSearch.getText()));
        }catch (Exception e){
            hireDto = null;
        }
        return hireDto;
    }

    public void MarkAsReturnedOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Did customer pay the balance?",ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(
                response->{
                    if (response==ButtonType.YES){
                        new Alert(Alert.AlertType.CONFIRMATION,"Did you return the Deposit?",ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(
                                response2 ->{
                                    new Alert(Alert.AlertType.CONFIRMATION,"Did customer handed over the vehicle?",ButtonType.YES,ButtonType.NO).showAndWait().ifPresent(
                                            response3->{
                                                HireDto hireDto = new HireDto();
                                                hireDto.setHireId(Integer.parseInt(txtHireID.getText()));
                                                hireDto.setTotal(Double.parseDouble(txtTotal.getText()));
                                                hireDto.setBalance(0);
                                                try {
                                                    hireService.markAsReturned(hireDto);
                                                    new Alert(Alert.AlertType.INFORMATION,"Hire marked as Returned").show();
                                                    loadAllHires();
                                                    clearFields();

                                                } catch (Exception e) {
                                                    new Alert(Alert.AlertType.ERROR,e.getMessage());
                                                }
                                            }
                                    );
                                }
                        );
                    }
        });
    }

    private void clearFields(){
        txtHireID.clear();
        txtVehicle.clear();
        txtCustomerName.clear();
        txtHirePlacedBy.clear();
        txtFrom.clear();
        txtTo.clear();
        txtISReturned.clear();
        txtTotal.clear();
        txtDailyRental.clear();
        txtDeposit.clear();
        txtAdvance.clear();
        txtBalance.clear();
    }
}
