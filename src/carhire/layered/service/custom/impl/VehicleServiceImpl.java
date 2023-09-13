package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.VehicleDao;
import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.dto.VehicleDto;
import carhire.layered.entity.VehicleBrandEntity;
import carhire.layered.entity.VehicleCategoryEntity;
import carhire.layered.entity.VehicleEntity;
import carhire.layered.service.custom.VehicleService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class VehicleServiceImpl implements VehicleService {
    VehicleDao vehicleDao= (VehicleDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();
    @Override
    public int addVehicle(VehicleDto vehicleDto) throws Exception {
        return vehicleDao.add(new VehicleEntity(
                vehicleDto.getVehicleId(),
                vehicleDto.getVehicleNumber(),
                new VehicleBrandEntity(vehicleDto.getVehicleBrandDto().getId(),vehicleDto.getVehicleBrandDto().getVehicleBrand()),
                vehicleDto.getYear(),
                vehicleDto.getModel(),
                new VehicleCategoryEntity(vehicleDto.getVehicleCategoryDto().getId(),vehicleDto.getVehicleCategoryDto().getVehicleCategory()),
                vehicleDto.getTransmission(),
                vehicleDto.getNoOfSeats(),
                vehicleDto.getDailyRental(),
                vehicleDto.getStatus()),session,transaction);
    }

    @Override
    public int updateVehicle(VehicleDto vehicleDto) throws Exception {
        return vehicleDao.update(new VehicleEntity(
                vehicleDto.getVehicleId(),
                vehicleDto.getVehicleNumber(),
                new VehicleBrandEntity(vehicleDto.getVehicleBrandDto().getId(),vehicleDto.getVehicleBrandDto().getVehicleBrand()),
                vehicleDto.getYear(),
                vehicleDto.getModel(),
                new VehicleCategoryEntity(vehicleDto.getVehicleCategoryDto().getId(),vehicleDto.getVehicleCategoryDto().getVehicleCategory()),
                vehicleDto.getTransmission(),
                vehicleDto.getNoOfSeats(),
                vehicleDto.getDailyRental(),
                vehicleDto.getStatus()),session,transaction);
    }

    @Override
    public VehicleDto getVehicle(String vehicleNumber) throws Exception {
        VehicleEntity vehicleEntity = vehicleDao.get(vehicleNumber,session);
        return new VehicleDto(
                vehicleEntity.getVehicleId(),
                vehicleEntity.getVehicleNumber(),
                new VehicleBrandDto(vehicleEntity.getVehicleBrandEntity().getId(),vehicleEntity.getVehicleBrandEntity().getVehicleBrand()),
                vehicleEntity.getYear(),
                vehicleEntity.getModel(),
                new VehicleCategoryDto(vehicleEntity.getVehicleCategoryEntity().getCategoryID(),vehicleEntity.getVehicleCategoryEntity().getVehicleCategory()),
                vehicleEntity.getTransmission(),
                vehicleEntity.getNoOfSeats(),
                vehicleEntity.getDailyRental(),
                vehicleEntity.getStatus());
    }

    @Override
    public int deleteVehicle(int vehicleID) throws Exception {
        return vehicleDao.delete(vehicleID,session,transaction);
    }

    @Override
    public ArrayList<VehicleDto> getAllVehicles() throws Exception {
        ArrayList<VehicleEntity> result = vehicleDao.getAll(session);
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
        for (VehicleEntity vehicleEntity:result){
            vehicleDtos.add(new VehicleDto(
                    vehicleEntity.getVehicleId(),
                    vehicleEntity.getVehicleNumber(),
                    new VehicleBrandDto(vehicleEntity.getVehicleBrandEntity().getId(),vehicleEntity.getVehicleBrandEntity().getVehicleBrand()),
                    vehicleEntity.getYear(),
                    vehicleEntity.getModel(),
                    new VehicleCategoryDto(vehicleEntity.getVehicleCategoryEntity().getCategoryID(),vehicleEntity.getVehicleCategoryEntity().getVehicleCategory()),
                    vehicleEntity.getTransmission(),
                    vehicleEntity.getNoOfSeats(),
                    vehicleEntity.getDailyRental(),
                    vehicleEntity.getStatus()
            ));
        }
        return vehicleDtos;
    }

    @Override
    public VehicleDto getVehicle(int vehicleID) throws Exception {
        VehicleEntity vehicleEntity = vehicleDao.getVehicleByID(vehicleID,session);
        return new VehicleDto(
                vehicleEntity.getVehicleId(),
                vehicleEntity.getVehicleNumber(),
                new VehicleBrandDto(vehicleEntity.getVehicleBrandEntity().getId(),vehicleEntity.getVehicleBrandEntity().getVehicleBrand()),
                vehicleEntity.getYear(),
                vehicleEntity.getModel(),
                new VehicleCategoryDto(vehicleEntity.getVehicleBrandEntity().getId(),vehicleEntity.getVehicleCategoryEntity().getVehicleCategory()),
                vehicleEntity.getTransmission(),
                vehicleEntity.getNoOfSeats(),
                vehicleEntity.getDailyRental(),
                vehicleEntity.getStatus());
    }

    @Override
    public int makeVehicleIn(int vehicleId) throws Exception {
        return vehicleDao.makeVehicleIn(vehicleId,session,transaction);
    }

    @Override
    public ArrayList<VehicleDto> getAvailableVehicles() throws Exception {
        ArrayList<VehicleEntity> result = vehicleDao.getAvailableVehicles(session);
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
        for (VehicleEntity vehicleEntity:result){
            vehicleDtos.add(new VehicleDto(
                    vehicleEntity.getVehicleId(),
                    vehicleEntity.getVehicleNumber(),
                    new VehicleBrandDto(vehicleEntity.getVehicleBrandEntity().getId(),vehicleEntity.getVehicleBrandEntity().getVehicleBrand()),
                    vehicleEntity.getYear(),
                    vehicleEntity.getModel(),
                    new VehicleCategoryDto(vehicleEntity.getVehicleCategoryEntity().getCategoryID(),vehicleEntity.getVehicleCategoryEntity().getVehicleCategory()),
                    vehicleEntity.getTransmission(),
                    vehicleEntity.getNoOfSeats(),
                    vehicleEntity.getDailyRental(),
                    vehicleEntity.getStatus()
            ));
        }
        return vehicleDtos;
    }
}
