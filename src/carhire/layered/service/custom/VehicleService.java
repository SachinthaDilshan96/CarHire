package carhire.layered.service.custom;

import carhire.layered.dto.VehicleDto;
import carhire.layered.service.SuperService;

import java.util.ArrayList;

public interface VehicleService extends SuperService {
    int addVehicle(VehicleDto vehicleDto) throws Exception;
    int updateVehicle(VehicleDto vehicleDto) throws Exception;
    VehicleDto getVehicle(String vehicleNumber) throws Exception;
    int deleteVehicle(int vehicleID) throws Exception;
    ArrayList<VehicleDto> getAllVehicles() throws Exception;
    VehicleDto getVehicle(int vehicleID) throws Exception;
    int makeVehicleIn(int vehicleId) throws Exception;
}
