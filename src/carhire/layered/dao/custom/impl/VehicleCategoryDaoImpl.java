package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.VehicleCategoryDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryDaoImpl implements VehicleCategoryDao {


    @Override
    public VehicleCategoryEntity get(String vehicleCategory, Session session) throws Exception {
        return (VehicleCategoryEntity)CrudUtil.getUniqueResult("FROM VehicleCategoryEntity WHERE vehicleCategory=?1",session,vehicleCategory);
    }

    @Override
    public int add(VehicleCategoryEntity vehicleCategoryEntity, Session session, Transaction transaction) throws Exception {
        return CrudUtil.save(vehicleCategoryEntity,session, transaction);
    }

    @Override
    public int update(VehicleCategoryEntity vehicleCategoryEntity , Session session,Transaction transaction) throws Exception {
        return CrudUtil.executeUpdate("UPDATE VehicleCategoryEntity set vehicleCategory=?1 where categoryID=?2",
                session,transaction,vehicleCategoryEntity.getVehicleCategory(),vehicleCategoryEntity.getCategoryID());
    }

    @Override
    public int delete(String s, Session session) throws Exception {
        return 0;
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
