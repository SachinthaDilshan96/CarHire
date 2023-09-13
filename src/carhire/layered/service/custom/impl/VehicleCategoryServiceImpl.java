package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.VehicleCategoryDao;
import carhire.layered.dto.VehicleCategoryDto;
import carhire.layered.entity.VehicleCategoryEntity;
import carhire.layered.service.custom.VehicleCategoryService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class VehicleCategoryServiceImpl implements VehicleCategoryService {
    VehicleCategoryDao vehicleCategoryDao =(VehicleCategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE_CATEGORY);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

    @Override
    public VehicleCategoryDto getVehicleCategory(String vehicleCategory) throws Exception {
        VehicleCategoryEntity vehicleCategoryEntity = vehicleCategoryDao.get(vehicleCategory,session);
        if (vehicleCategoryEntity==null){
            return null;
        }else{
            return new VehicleCategoryDto(vehicleCategoryEntity.getCategoryID(),vehicleCategoryEntity.getVehicleCategory());
        }
    }

    @Override
    public int addVehicleCategory(VehicleCategoryDto vehicleCategoryDto) throws Exception {
        return vehicleCategoryDao.add(new VehicleCategoryEntity(0,vehicleCategoryDto.getVehicleCategory()),session,transaction);
    }

    @Override
    public int updateVehicleCategory(VehicleCategoryDto vehicleCategoryDto) throws Exception {
        return vehicleCategoryDao.update(new VehicleCategoryEntity(vehicleCategoryDto.getId(),vehicleCategoryDto.getVehicleCategory()),session,transaction);
    }

    @Override
    public ArrayList<VehicleCategoryDto> getAllVehicleCategory() throws Exception {
        ArrayList<VehicleCategoryEntity> vehicleCategoryEntities = vehicleCategoryDao.getAll(session);
        ArrayList<VehicleCategoryDto> dtos = new ArrayList<>();
        for (VehicleCategoryEntity v:vehicleCategoryEntities) {
            dtos.add(new VehicleCategoryDto(v.getCategoryID(),v.getVehicleCategory()));
        }
        return dtos;
    }
}
