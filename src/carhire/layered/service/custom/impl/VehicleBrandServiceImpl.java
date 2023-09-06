package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.VehicleBrandDao;
import carhire.layered.dto.VehicleBrandDto;
import carhire.layered.entity.VehicleBrandEntity;
import carhire.layered.service.ServiceFactory;
import carhire.layered.service.custom.VehicleBrandService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;

public class VehicleBrandServiceImpl implements VehicleBrandService {
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    VehicleBrandDao vehicleBrandDao = (VehicleBrandDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE_BRAND);
    @Override
    public VehicleBrandDto getVehicleBrand(String brand) throws Exception {
        VehicleBrandEntity vehicleBrandEntity = vehicleBrandDao.get(brand,session);
        if (vehicleBrandEntity==null)return null;
        else return new VehicleBrandDto(vehicleBrandEntity.getId(), vehicleBrandEntity.getVehicleBrand());
    }

    @Override
    public int addVehicleBrand(VehicleBrandDto vehicleBrandDto) throws Exception {
        return vehicleBrandDao.add(
                new VehicleBrandEntity(vehicleBrandDto.getId(), vehicleBrandDto.getVehicleBrand()),
                session);
    }

    @Override
    public int updateVehicleBrand(VehicleBrandDto vehicleBrandDto) throws Exception {
        return vehicleBrandDao.update(
                new VehicleBrandEntity(vehicleBrandDto.getId(), vehicleBrandDto.getVehicleBrand()),
                session);
    }

    @Override
    public ArrayList<VehicleBrandDto> getAllVehicleBrands() throws Exception {
        ArrayList<VehicleBrandDto> vehicleBrandDtos = new ArrayList<>();
        ArrayList<VehicleBrandEntity> vehicleBrandEntities = vehicleBrandDao.getAll(session);
        for (VehicleBrandEntity vehicleBrand:vehicleBrandEntities){
            vehicleBrandDtos.add(new VehicleBrandDto(vehicleBrand.getId(),vehicleBrand.getVehicleBrand()));
        }
        return vehicleBrandDtos;
    }
}
