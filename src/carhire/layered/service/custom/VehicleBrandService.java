package carhire.layered.service.custom;

import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.service.SuperService;

import java.util.ArrayList;

public interface VehicleBrandService extends SuperService {
    VehicleBrandDto getVehicleBrand(String brand) throws Exception;
    int addVehicleBrand(VehicleBrandDto vehicleBrandDto) throws Exception;
    int updateVehicleBrand(VehicleBrandDto vehicleBrandDto) throws Exception;
    ArrayList<VehicleBrandDto> getAllVehicleBrands() throws Exception;
}
