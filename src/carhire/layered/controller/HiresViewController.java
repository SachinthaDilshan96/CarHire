package carhire.layered.controller;

import carhire.layered.dto.HireDto;
import carhire.layered.dto.VehicleDto;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class HiresViewController {
    public AnchorPane hiresViewContext;
    public TextField txtCustomerName;
    public TextField txtStatus;
    public TableView tblHires;
    public TextField txtCharge;
    public TextField txtHirePlacedBy;
    public TextField txtVehicleNumber;
    public TextField txtTo;
    public TextField txtFrom;
    public TextField txtHireIdSearch;
    public TextField txtISReturned;
    public TextField txtTotal;
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
    public ComboBox comboHireFilter;

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

        comboHireFilter.setItems(FXCollections.observableArrayList(new String[]{"All Vehicles", "Overdue Vehicles"}));
        comboHireFilter.getSelectionModel().select(0);
        comboHireFilter.setOnAction((event -> {
            loadData(comboHireFilter.getSelectionModel().getSelectedIndex());
        }));
        loadAllHires();

        tblHires.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) ->{
                    if (newValue!=null)setData((HireTm) newValue);
                } ));
    }

    private void loadData(int selectedIndex) {
        switch (selectedIndex){
            case 0:
                tblHires.getItems().clear();
                loadAllHires();
                break;
            case 1:
                tblHires.getItems().clear();
                loadOverDueHires();
                break;
        }
    }

    private void loadOverDueHires() {
        ObservableList<HireTm> hireTms = null;
        try{
            ArrayList<HireDto> hireDtos = hireService.getAllOverdueHires(LocalDate.now());
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

    private void setData(HireTm hireTm){
        txtHireID.setText(Integer.toString(hireTm.getHireId()));
        txtVehicle.setText(hireTm.getVehicle().getVehicleNumber());
        txtCustomerName.setText(hireTm.getCustomer().getFirstName());
        txtHirePlacedBy.setText(hireTm.getOrderPlacedBy().getFirstName());
        txtFrom.setText(hireTm.getFromDate().toString());
        txtTo.setText(hireTm.getToDate().toString());
        txtISReturned.setText(hireTm.getIsReturned());
        txtTotal.setText(Double.toString(hireTm.getTotal()));
        txtDailyRental.setText(Double.toString(hireTm.getDailyRental()));
        txtDeposit.setText(Double.toString(hireTm.getDeposit()));
        txtAdvance.setText(Double.toString(hireTm.getAdvance()));
        txtBalance.setText(Double.toString(hireTm.getBalance()));
    }

    private void loadAllHires(){
        ObservableList<HireTm> hireTms = null;
        try{
            ArrayList<HireDto> hireDtos = hireService.getAllHires();
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
        Stage stage = (Stage) hiresViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
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
}
