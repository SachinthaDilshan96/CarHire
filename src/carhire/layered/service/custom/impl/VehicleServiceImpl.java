package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.VehicleDao;
import carhire.layered.dto.VehicleDto;
import carhire.layered.entity.VehicleEntity;
import carhire.layered.service.custom.VehicleService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;

public class VehicleServiceImpl implements VehicleService {
    VehicleDao vehicleDao= (VehicleDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public int addVehicle(VehicleDto vehicleDto) throws Exception {
        return vehicleDao.add(new VehicleEntity(
                vehicleDto.getVehicleId(),
                vehicleDto.getVehicleNumber(),
                vehicleDto.getBrandId(),
                vehicleDto.getYear(),
                vehicleDto.getModel(),
                vehicleDto.getVehicleTypeId(),
                vehicleDto.getTransmission(),
                vehicleDto.getNoOfSeats(),
                vehicleDto.getDailyRental(),
                vehicleDto.getStatus()),session);
    }

    @Override
    public int updateVehicle(VehicleDto vehicleDto) throws Exception {
        return vehicleDao.update(new VehicleEntity(
                vehicleDto.getVehicleId(),
                vehicleDto.getVehicleNumber(),
                vehicleDto.getBrandId(),
                vehicleDto.getYear(),
                vehicleDto.getModel(),
                vehicleDto.getVehicleTypeId(),
                vehicleDto.getTransmission(),
                vehicleDto.getNoOfSeats(),
                vehicleDto.getDailyRental(),
                vehicleDto.getStatus()
        ),session);
    }

    @Override
    public VehicleDto getVehicle(String vehicleNumber) throws Exception {
        VehicleEntity vehicleEntity = vehicleDao.get(vehicleNumber,session);
        return new VehicleDto(
                vehicleEntity.getVehicleId(),
                vehicleEntity.getVehicleNumber(),
                vehicleEntity.getBrandId(),
                vehicleEntity.getYear(),
                vehicleEntity.getModel(),
                vehicleEntity.getVehicleTypeId(),
                vehicleEntity.getTransmission(),
                vehicleEntity.getNoOfSeats(),
                vehicleEntity.getDailyRental(),
                vehicleEntity.getStatus());
    }

    @Override
    public int deleteVehicle(int vehicleID) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<VehicleDto> getAllVehicles() throws Exception {
        ArrayList<VehicleEntity> result = vehicleDao.getAll(session);
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
        for (VehicleEntity vehicleEntity:result){
            vehicleDtos.add(new VehicleDto(
                    vehicleEntity.getVehicleId(),
                    vehicleEntity.getVehicleNumber(),
                    vehicleEntity.getBrandId(),
                    vehicleEntity.getYear(),
                    vehicleEntity.getModel(),
                    vehicleEntity.getVehicleTypeId(),
                    vehicleEntity.getTransmission(),
                    vehicleEntity.getNoOfSeats(),
                    vehicleEntity.getDailyRental(),
                    vehicleEntity.getStatus()
            ));
        }
        return vehicleDtos;
    }
}
