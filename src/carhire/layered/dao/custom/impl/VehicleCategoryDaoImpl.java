package carhire.layered.dao.custom.impl;

import carhire.layered.dao.custom.VehicleCategoryDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public class VehicleCategoryDaoImpl implements VehicleCategoryDao {
    @Override
    public VehicleCategoryEntity get(String s, Session session, boolean a) throws Exception {
        return null;
    }

    @Override
    public int add(VehicleCategoryEntity vehicleCategoryEntity) throws Exception {
        return 0;
    }

    @Override
    public int update(VehicleCategoryEntity vehicleCategoryEntity) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<VehicleCategoryEntity> getAll() throws Exception {
        return null;
    }
}
