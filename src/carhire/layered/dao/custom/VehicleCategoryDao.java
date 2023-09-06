package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public interface VehicleCategoryDao extends CrudDao<VehicleCategoryEntity,String, Session> {
    VehicleCategoryEntity get(String vehicleCategory, Session session) throws Exception;
    int add(VehicleCategoryEntity vehicleCategoryEntity , Session session) throws Exception;
    int update(VehicleCategoryEntity vehicleCategoryEntity , Session session) throws Exception;
    ArrayList<VehicleCategoryEntity> getAll(Session session) throws Exception;
}
