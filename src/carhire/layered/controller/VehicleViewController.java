package carhire.layered.controller;

import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.dto.VehicleDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.VehicleBrandService;
import carhire.layered.service.custom.VehicleCategoryService;
import carhire.layered.service.custom.VehicleService;
import carhire.layered.view.tm.VehicleBrandTm;
import carhire.layered.view.tm.VehicleCategoryTm;
import carhire.layered.view.tm.VehicleTm;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VehicleViewController {
    private final int modelStartYear = 1975;
    private final int maxNumberOfSeats = 65;
    public AnchorPane vehicleViewContext;
    public TextField txtVehicleId;
    public TextField txtVehicleNumber;
    public TextField txtDailyRental;
    public TextField txtStatus;
    public ComboBox txtBrand;
    public ComboBox txtVehicleType;
    public ComboBox txtTransmission;
    public Button btnAddVehicle;
    public Label lblVehicleNumber;
    public Label lblYear;
    public Label lblNumberOfSeats;
    public Label lblDailyRental;
    public TextField txtModel;
    public ComboBox txtModelYear;
    public Label lblModel;
    public ComboBox txtNoOfSeats;
    public Label lblModelYear;
    public Label lblBrand;
    public Label lblVehicleType;
    public Label lblTransmission;
    public TableColumn colId;
    public TableColumn colNumber;
    public TableColumn<VehicleTm,String> colBrand;
    public TableColumn colModel;
    public TableColumn colTransmission;
    public TableColumn colDailyRental;
    public TableColumn colStatus;
    public TableColumn colDelete;
    public TableView tblVehicle;

    private VehicleBrandService vehicleBrandService = (VehicleBrandService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_BRAND);
    private VehicleCategoryService vehicleCategoryService = (VehicleCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_CATEGORY);
    private VehicleService vehicleService = (VehicleService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_SERVICE);

    public void initialize(){
        setVehicleBrands();
        setVehicleCategories();
        setTransmission();
        setNumberOfSeats();
        setModelYear();
        loadAllVehicles();
        colId.setCellValueFactory(new PropertyValueFactory<VehicleTm,Integer>("vehicleId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<VehicleTm,String>("vehicleNumber"));
        colModel.setCellValueFactory(new PropertyValueFactory<VehicleTm,String>("model"));
        colBrand.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleBrandTm().getBrandName()));
        colTransmission.setCellValueFactory(new PropertyValueFactory<VehicleTm,String>("transmission"));
        colDailyRental.setCellValueFactory(new PropertyValueFactory<VehicleTm,Double>("dailyRental"));
        colStatus.setCellValueFactory(new PropertyValueFactory<VehicleTm,String>("status"));

        tblVehicle.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue!=null) setData((VehicleTm)newValue);
                })
        );
    }

    private void setData(VehicleTm newValue) {
        txtVehicleId.setText(Integer.toString(newValue.getVehicleId()));
        txtVehicleNumber.setText(newValue.getVehicleNumber());


    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) vehicleViewContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
    }

    public void UpdateVehicleOnAction(ActionEvent actionEvent) {
    }

    public void DeleteVehicleOnAction(ActionEvent actionEvent) {
    }

    public void BackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardView");
    }

    private void setVehicleBrands(){
        try {
            ArrayList<VehicleBrandDto> vehicleBrandDtos = vehicleBrandService.getAllVehicleBrands();
            if (vehicleBrandDtos.size()>=0){
                txtBrand.getItems().addAll(vehicleBrandDtos);
                txtBrand.valueProperty().addListener((observable, oldValue, newValue) -> {
                    //System.out.println(newValue.toString());
                });
                txtBrand.setConverter(new StringConverter<VehicleBrandDto>() {
                    @Override
                    public String toString(VehicleBrandDto brandDto) {
                        return brandDto.getVehicleBrand();
                    }

                    @Override
                    public VehicleBrandDto fromString(String string) {
                        return null;
                    }
                });
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.INFORMATION,"An error occurred while loading");
        }
    }

    private void setVehicleCategories(){
        try {
            ArrayList<VehicleCategoryDto> vehicleCategoryDtos = vehicleCategoryService.getAllVehicleCategory();
            if (vehicleCategoryDtos.size()>=0){
                txtVehicleType.getItems().addAll(vehicleCategoryDtos);
                txtVehicleType.valueProperty().addListener((observable, oldValue, newValue) -> {
                    //System.out.println(newValue.toString());
                });
                txtVehicleType.setConverter(new StringConverter<VehicleCategoryDto>() {
                    @Override
                    public String toString(VehicleCategoryDto vehicleCategoryDto) {
                        return vehicleCategoryDto.getVehicleCategory();
                    }

                    @Override
                    public VehicleCategoryDto fromString(String string) {
                        return null;
                    }
                });
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.INFORMATION,"An error occurred while loading");
        }

    }

    private void setModelYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        txtModelYear.getItems().addAll(getNumbersUsingIntStreamRange(modelStartYear,(year+1)));
    }
    private List<Integer> getNumbersUsingIntStreamRange(int start, int end) {
        return IntStream.range(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isVehicleNumberValid(){
        if (Pattern.compile("[!@#$%^&*()+=.,/?'\\|`]").matcher(txtVehicleNumber.getText()).find() | (txtVehicleNumber.getText().length()==0)){
            lblVehicleNumber.setVisible(true);
            return false;
        }else{
            lblVehicleNumber.setVisible(false);
            return true;
        }
    }

    private boolean isVehicleModelValid(){
        if (txtModel.getText().length()==0) {
            lblModel.setVisible(true);
            return false;
        }else{
            lblModel.setVisible(false);
            return true;
        }
    }
    private boolean isNumberOfSeatsValid(){
        if (txtNoOfSeats.getSelectionModel().isEmpty()){
            lblNumberOfSeats.setVisible(true);
            return false;
        }else{
            lblNumberOfSeats.setVisible(false);
            return true;
        }
    }

    private boolean isDailyRentalValid(){
        if (Pattern.compile("[^0-9]").matcher(txtDailyRental.getText()).find() | (txtDailyRental.getText().length()==0)){
            lblDailyRental.setVisible(true);
            return false;
        }else{
            lblDailyRental.setVisible(false);
            return true;
        }
    }

    private boolean isBrandValid(){
        if (txtBrand.getSelectionModel().isEmpty()){
            lblBrand.setVisible(true);
            return false;
        }else{
            lblBrand.setVisible(false);
            return true;
        }
    }

    private boolean isVehicleType(){
        if (txtVehicleType.getSelectionModel().isEmpty()){
            lblVehicleType.setVisible(true);
            return false;
        }else{
            lblVehicleType.setVisible(false);
            return true;
        }
    }

    private boolean isTransmission(){
        if (txtTransmission.getSelectionModel().isEmpty()){
            lblTransmission.setVisible(true);
            return false;
        }else{
            lblTransmission.setVisible(false);
            return true;
        }
    }
    private boolean isModelYear(){
        if (txtModelYear.getSelectionModel().isEmpty()){
            lblModelYear.setVisible(true);
            return false;
        }else{
            lblModelYear.setVisible(false);
            return true;
        }
    }
    private void setTransmission(){
        txtTransmission.getItems().addAll("Automatic","Manual");
    }

    private void setNumberOfSeats(){
        txtNoOfSeats.getItems().addAll(getNumbersUsingIntStreamRange(1,maxNumberOfSeats));
    }


    public void clearFields() {
        txtVehicleId.clear();
        txtVehicleNumber.clear();
        txtBrand.valueProperty().set(null);
        txtModelYear.valueProperty().set(null);
        txtModel.clear();
        txtVehicleType.valueProperty().set(null);
        txtTransmission.valueProperty().set(null);
        txtNoOfSeats.valueProperty().set(null);
        txtDailyRental.clear();
        txtStatus.clear();
    }

    public void addVehicleOnAction(ActionEvent actionEvent) {
        if (btnAddVehicle.getText().equalsIgnoreCase("Add Vehicle")){
            if (isVehicleNumberValid() & isNumberOfSeatsValid()
                    & isVehicleModelValid() & isDailyRentalValid()
                    & isBrandValid() & isVehicleType() & isTransmission() & isModelYear()){
                if (getVehicle()==null){
                    try {
                        VehicleBrandDto vehicleBrandDto = vehicleBrandService.getVehicleBrand(((VehicleBrandDto)txtBrand.getSelectionModel().getSelectedItem()).getVehicleBrand());
                        VehicleCategoryDto vehicleCategoryDto = vehicleCategoryService.getVehicleCategory( ((VehicleCategoryDto)txtVehicleType.getSelectionModel().getSelectedItem()).getVehicleCategory());
                        if (vehicleBrandDto!=null & vehicleCategoryDto!=null){
                            VehicleBrandDto vehicleBrandDtoToBeSaved = new VehicleBrandDto();
                            vehicleBrandDtoToBeSaved.setId(vehicleBrandDto.getId());
                            vehicleBrandDtoToBeSaved.setVehicleBrand(vehicleBrandDto.getVehicleBrand());
                            VehicleCategoryDto vehicleCategoryDtoToBeSaved = new VehicleCategoryDto();
                            vehicleCategoryDtoToBeSaved.setId(vehicleCategoryDto.getId());
                            vehicleCategoryDtoToBeSaved.setVehicleCategory(vehicleCategoryDto.getVehicleCategory());
                            int i = vehicleService.addVehicle(new VehicleDto(
                                    0,
                                    txtVehicleNumber.getText(),
                                    vehicleBrandDtoToBeSaved,
                                    ((Integer) txtModelYear.getSelectionModel().getSelectedItem()),
                                    txtModel.getText(),
                                    vehicleCategoryDtoToBeSaved,
                                    (String) txtTransmission.getSelectionModel().getSelectedItem(),
                                    (Integer)txtNoOfSeats.getSelectionModel().getSelectedItem(),
                                    Double.parseDouble(txtDailyRental.getText()),
                                    txtStatus.getText()
                            ));
                            if (i>=0){
                                new Alert(Alert.AlertType.INFORMATION,"Vehicle Added Successfully").show();
                                loadAllVehicles();
                                clearFields();
                            }else {
                                new Alert(Alert.AlertType.ERROR,"Vehicle Saving failed").show();
                            }
                        }
                    }catch (Exception e){
                        new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Vehicle number already exists").show();
                    lblVehicleNumber.setVisible(true);
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid User Inputs. Please check and try again.").show();
            }
        }else{

        }
    }

    private void loadAllVehicles(){
        ObservableList<VehicleTm> observableList = FXCollections.observableArrayList();
        try {
            ArrayList<VehicleDto> vehicleTms = vehicleService.getAllVehicles();
            if (vehicleTms.size()>0){
                for (VehicleDto vehicleDto:vehicleTms){
                    observableList.add(new VehicleTm(
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
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An error occurred while loading.").show();
        }
        tblVehicle.setItems(observableList);
    }

    private VehicleDto getVehicle(){
        VehicleDto vehicleDto;
        try {
            vehicleDto = vehicleService.getVehicle(txtVehicleNumber.getText());
        }catch (Exception e){
            vehicleDto = null;
            new Alert(Alert.AlertType.ERROR,"An error occurred").show();
        }
        return vehicleDto;
    }

    public void clearFieldsOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
