package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.TreeMap;

public interface VehicleCategoryDao extends CrudDao<VehicleCategoryEntity,String, Session, Transaction > {
    VehicleCategoryEntity get(String vehicleCategory, Session session) throws Exception;
    int add(VehicleCategoryEntity vehicleCategoryEntity , Session session, Transaction transaction) throws Exception;
    int update(VehicleCategoryEntity vehicleCategoryEntity , Session session,Transaction transaction) throws Exception;
    ArrayList<VehicleCategoryEntity> getAll(Session session) throws Exception;
}
