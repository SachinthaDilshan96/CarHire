package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleBrandEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public interface VehicleBrandDao extends CrudDao<VehicleBrandEntity,String, Session, Transaction> {
     VehicleBrandEntity get(String brandName, Session session) throws Exception;
     int add(VehicleBrandEntity vehicleBrand, Session session,Transaction transaction) throws Exception;
     int update(VehicleBrandEntity vehicleBrand, Session session,Transaction transaction) throws Exception;
     ArrayList<VehicleBrandEntity> getAll(Session session) throws Exception;
}
