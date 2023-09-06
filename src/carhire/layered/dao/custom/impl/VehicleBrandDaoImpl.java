package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.VehicleBrandDao;
import carhire.layered.entity.VehicleBrandEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VehicleBrandDaoImpl implements VehicleBrandDao {
    @Override
    public VehicleBrandEntity get(String s, Session session, boolean a) throws Exception {
        return null;
    }


    @Override
    public VehicleBrandEntity get(String brandName, Session session) throws Exception {
        return (VehicleBrandEntity) CrudUtil.getUniqueResult("FROM VehicleBrandEntity WHERE vehicleBrand=?1",session,brandName);
    }

    @Override
    public int add(VehicleBrandEntity vehicleBrand, Session session) throws Exception {
        int i = CrudUtil.save(vehicleBrand,session);
        System.out.println(i+" thi is i");
        return i;
    }

    @Override
    public int update(VehicleBrandEntity vehicleBrand, Session session) throws Exception {
        return CrudUtil.executeUpdate(
                "update VehicleBrandEntity set vehicleBrand=?1 where id=?2",
                session,
                vehicleBrand.getVehicleBrand(),vehicleBrand.getId());
    }

    @Override
    public ArrayList<VehicleBrandEntity> getAll(Session session) throws Exception {
        List<Object> results = CrudUtil.getListResult("FROM VehicleBrandEntity",session);
        ArrayList<VehicleBrandEntity> vehicleBrandEntities = new ArrayList<>();
        for (Object o:results) {
            VehicleBrandEntity vehicleBrand = (VehicleBrandEntity) o;
            vehicleBrandEntities.add(new VehicleBrandEntity(vehicleBrand.getId(),vehicleBrand.getVehicleBrand()));
        }
        return vehicleBrandEntities;
    }
}
