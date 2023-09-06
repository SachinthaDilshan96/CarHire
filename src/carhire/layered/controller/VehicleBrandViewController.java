package carhire.layered.controller;

import carhire.layered.dto.UserDto;
import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.VehicleBrandService;
import carhire.layered.view.tm.VehicleBrandTm;
import carhire.layered.view.tm.VehicleCategoryTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class VehicleBrandViewController {
    public TextField txtVehicleBrandID;
    public TextField txtVehicleBrand;
    public Button btnAddNew;
    public TableView tblVehicleBrand;
    public TableColumn colId;
    public TableColumn colBrand;
    public AnchorPane vehicleBrandContext;

    VehicleBrandService vehicleBrandService =(VehicleBrandService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.VEHICLE_BRAND);
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<VehicleBrandTm,String>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<VehicleBrandTm,String>("brandName"));
        loadAllVehicleBrands();
        tblVehicleBrand.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue!=null){
                        setData((VehicleBrandTm)newValue);
                    }
                })
        );
    }
    private void setData(VehicleBrandTm newValue) {
        txtVehicleBrandID.setText(Integer.toString(newValue.getId()));
        txtVehicleBrand.setText(newValue.getBrandName());
        switchButtonText();
    }

    public void loadAllVehicleBrands(){
        ObservableList<VehicleBrandTm> observableList = FXCollections.observableArrayList();
         try {
             ArrayList<VehicleBrandDto> vehicleBrandDtos = vehicleBrandService.getAllVehicleBrands();
             for (VehicleBrandDto vehicleBrandDto:vehicleBrandDtos){
                 observableList.add(new VehicleBrandTm(vehicleBrandDto.getId(),vehicleBrandDto.getVehicleBrand()));
             }
         }catch (Exception e){
             new Alert(Alert.AlertType.ERROR,"An Error occurred while fetching the data").show();
         }
         tblVehicleBrand.setItems(observableList);
    }
    public void ClearFieldsOnAction(ActionEvent actionEvent) {
        clearFields();
        btnAddNew.setText("Add new Vehicle Brand");
    }

    public void AddNewVehicleTypeOnAction(ActionEvent actionEvent) {
        if (btnAddNew.getText().equalsIgnoreCase("Update Vehicle Brand")){
            updateVehicleBrand();
        }else{
            if (isVehicleBrandExist()){
                new Alert(Alert.AlertType.ERROR,"Vehicle Brand Already Exists").show();
            }else{
                try {
                    int i = vehicleBrandService.addVehicleBrand(
                            new VehicleBrandDto(0,
                                    txtVehicleBrand.getText().substring(0, 1).toUpperCase() + txtVehicleBrand.getText().substring(1).toLowerCase()));
                    if (i>=0){
                        new Alert(Alert.AlertType.INFORMATION,"Vehicle Brand Saved successfully").show();
                        clearFields();
                        btnAddNew.setText("Add new Vehicle Brand");
                        loadAllVehicleBrands();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }
    }

    private void updateVehicleBrand(){
        try{
            int i = vehicleBrandService.updateVehicleBrand(new VehicleBrandDto(
                    Integer.parseInt(txtVehicleBrandID.getText()),
                    txtVehicleBrand.getText().substring(0, 1).toUpperCase() + txtVehicleBrand.getText().substring(1).toLowerCase()
            ));
            if (i>=0){
                new Alert(Alert.AlertType.INFORMATION,"Vehicle Brand Updated").show();
                clearFields();
                loadAllVehicleBrands();
                btnAddNew.setText("Add new Vehicle Brand");
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean isVehicleBrandExist(){
        try{
            VehicleBrandDto vehicleBrandDto = vehicleBrandService.getVehicleBrand(txtVehicleBrand.getText());
            if(vehicleBrandDto==null) return false;
            else return true;
        }catch (Exception e){
            System.out.println("Vehicle Brand inspection failed. "+e.getMessage());
            return false;
        }
    }

    private void switchButtonText(){
        btnAddNew.setText("Update Vehicle Brand");
    }

    private void clearFields(){
        txtVehicleBrandID.clear();
        txtVehicleBrand.clear();
    }

}
