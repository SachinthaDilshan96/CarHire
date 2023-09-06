package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.VehicleCategoryService;
import carhire.layered.view.tm.UserTm;
import carhire.layered.view.tm.VehicleCategoryTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class VehicleCategoryViewController {
    public TextField txtVehicleCategoryID;
    public TextField txtVehicleCategory;
    public TableView tblVehicleCategory;
    public TableColumn colId;
    public TableColumn colType;
    public Button btnAddNew;
    VehicleCategoryService vehicleCategoryService =(VehicleCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_CATEGORY);

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<VehicleCategoryTm,String>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<VehicleCategoryTm,String>("vehicleType"));
        loadAllVehicleCategories();
        tblVehicleCategory.getSelectionModel().selectedItemProperty()
                .addListener((observable,oldValue,newValue)->{
                    if (newValue!=null){
                        setData((VehicleCategoryTm) newValue);
                    }
                });
    }

    private void setData(VehicleCategoryTm newValue) {
        txtVehicleCategoryID.setText(Integer.toString(newValue.getId()));
        txtVehicleCategory.setText(newValue.getVehicleType());
        switchButtonText();
    }

    private void loadAllVehicleCategories() {
        ObservableList<VehicleCategoryTm> observableList = FXCollections.observableArrayList();
        try{
            ArrayList<VehicleCategoryDto> vehicleCategoryDtos = vehicleCategoryService.getAllVehicleCategory();
            for (VehicleCategoryDto dto:vehicleCategoryDtos){
                observableList.add(new VehicleCategoryTm(
                        dto.getId(),
                        dto.getVehicleCategory()
                ));
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        tblVehicleCategory.setItems(observableList);
    }

    public void AddNewVehicleTypeOnAction(ActionEvent actionEvent) {
        if (btnAddNew.getText().equalsIgnoreCase("Update Vehicle Category")) {
            updateVehicleCategory();
        } else {
            if (isVehicleCategoryExist()) {
                new Alert(Alert.AlertType.WARNING, "Vehicle Type Already Exists").show();
            } else {
                try {
                    int i = vehicleCategoryService.addVehicleCategory(
                            new VehicleCategoryDto(
                                    0,
                                    txtVehicleCategory.getText().substring(0, 1).toUpperCase() + txtVehicleCategory.getText().substring(1).toLowerCase()));
                    if (i >= 0) {
                        new Alert(Alert.AlertType.INFORMATION, "Vehicle Category Saved Successfully").show();
                        clearFields();
                        loadAllVehicleCategories();
                        btnAddNew.setText("Add new Vehicle Category");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Vehicle Category Saving Failed. Please try again").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Vehicle Category Saving Failed. Please try again").show();
                }

            }

        }
    }

    private boolean isVehicleCategoryExist(){
        try{
            VehicleCategoryDto vehicleCategoryDto = vehicleCategoryService.getVehicleCategory(txtVehicleCategory.getText());
            if(vehicleCategoryDto==null) return false;
            else return true;
        }catch (Exception e){
            System.out.println("Vehicle Category inspection failed. "+e.getMessage());
            return false;
        }
    }

    public void updateVehicleCategory(){
        try {
            int i = vehicleCategoryService.updateVehicleCategory(new VehicleCategoryDto(
                    Integer.parseInt(txtVehicleCategoryID.getText()),
                    txtVehicleCategory.getText()));
            if (i>=0){
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Category Updated").show();
                clearFields();
                loadAllVehicleCategories();
                btnAddNew.setText("Add new Vehicle Category");
            }else {
                new Alert(Alert.AlertType.ERROR, "Vehicle Category Updation Failed").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void switchButtonText(){
        btnAddNew.setText("Update Vehicle Category");
    }

    private void clearFields(){
        txtVehicleCategoryID.clear();
        txtVehicleCategory.clear();
    }

    public void ClearFieldsOnAction(ActionEvent actionEvent) {
        clearFields();
        btnAddNew.setText("Add new Vehicle Category");
    }
}
