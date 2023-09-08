package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.VehicleDao;
import carhire.layered.entity.VehicleEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VehicleDaoImpl implements VehicleDao {

    @Override
    public VehicleEntity get(String s, Session session) throws Exception {
        return (VehicleEntity) CrudUtil.getUniqueResult("FROM VehicleEntity WHERE VehicleNumber=?1",session,s);
    }

    @Override
    public int add(VehicleEntity vehicleEntity, Session session) throws Exception {
        return CrudUtil.save(vehicleEntity,session);
    }

    @Override
    public int update(VehicleEntity vehicleEntity, Session session) throws Exception {
        return CrudUtil.executeUpdate("Update VehicleEntity set VehicleNumber=?1,Brand=?2,Year=?3,Model=?4,VehicleType=?5,Transmission=?6,NoOfSeats=?7,DailyRental=?8 Where VehicleID=?9",session);
    }

    @Override
    public int delete(String s, Session session) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<VehicleEntity> getAll(Session session) throws Exception {
        List<Object> result = CrudUtil.getListResult("From VehicleEntity",session);
        ArrayList<VehicleEntity> vehicleEntities = new ArrayList<>();
        for (Object object:result) {
            vehicleEntities.add((VehicleEntity)object );
        }
        return vehicleEntities;
    }


}
