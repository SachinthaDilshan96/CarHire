package carhire.layered.controller;

import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.VehicleBrandService;
import carhire.layered.service.custom.VehicleCategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    VehicleBrandService vehicleBrandService = (VehicleBrandService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_BRAND);
    VehicleCategoryService vehicleCategoryService = (VehicleCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_CATEGORY);

    public void initialize(){
        setVehicleBrands();
        setVehicleCategories();
        setTransmission();
        setNumberOfSeats();
        setModelYear();
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
                    System.out.println(newValue.toString());
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
                    System.out.println(newValue.toString());
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
        if (isVehicleNumberValid() & isNumberOfSeatsValid()
                & isVehicleModelValid() & isDailyRentalValid()
                & isBrandValid() & isVehicleType() & isTransmission() & isModelYear()){
            new Alert(Alert.AlertType.INFORMATION,"C").show();
        }
    }

    public void clearFieldsOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
