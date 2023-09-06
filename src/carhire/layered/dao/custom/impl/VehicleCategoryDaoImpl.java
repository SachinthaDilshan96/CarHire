package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.VehicleCategoryDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryDaoImpl implements VehicleCategoryDao {
    @Override
    public VehicleCategoryEntity get(String s, Session session, boolean a) throws Exception {
        return null;
    }

    @Override
    public VehicleCategoryEntity get(String vehicleCategory, Session session) throws Exception {
        return (VehicleCategoryEntity)CrudUtil.getUniqueResult("FROM VehicleCategoryEntity WHERE vehicleCategory=?1",session,vehicleCategory);
    }

    @Override
    public int add(VehicleCategoryEntity vehicleCategoryEntity, Session session) throws Exception {
        return CrudUtil.save(vehicleCategoryEntity,session);
    }

    @Override
    public int update(VehicleCategoryEntity vehicleCategoryEntity , Session session) throws Exception {
        return CrudUtil.executeUpdate("UPDATE VehicleCategoryEntity set vehicleCategory=?1 where categoryID=?2",
                session,vehicleCategoryEntity.getVehicleCategory(),vehicleCategoryEntity.getCategoryID());
    }

    @Override
    public ArrayList<VehicleCategoryEntity> getAll(Session session) throws Exception {
        List<Object> result = CrudUtil.getListResult("FROM VehicleCategoryEntity",session);
        ArrayList<VehicleCategoryEntity> vehicleCategoryEntities = new ArrayList<>();
        for (Object object:result) {
            VehicleCategoryEntity v = (VehicleCategoryEntity)object;
            vehicleCategoryEntities.add(new VehicleCategoryEntity(
                    v.getCategoryID(),v.getVehicleCategory()));
        }
        return vehicleCategoryEntities;
    }
}
