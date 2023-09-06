package carhire.layered.service.custom;

import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.service.SuperService;

import java.util.ArrayList;

public interface VehicleCategoryService extends SuperService {
    VehicleCategoryDto getVehicleCategory(String vehicleCategory) throws Exception;
    int addVehicleCategory(VehicleCategoryDto vehicleCategoryDto) throws Exception;
    int updateVehicleCategory(VehicleCategoryDto vehicleCategoryDto) throws Exception;
    ArrayList<VehicleCategoryDto> getAllVehicleCategory() throws Exception;

}
