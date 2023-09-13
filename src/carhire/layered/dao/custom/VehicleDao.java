package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public interface VehicleDao extends CrudDao<VehicleEntity,String, Session, Transaction> {
    int delete(Integer id, Session session,Transaction transaction) throws Exception;

     VehicleEntity getVehicleByID(int vehicleID, Session session) throws Exception;
     int makeVehicleIn(int id, Session session, Transaction transaction) throws Exception;
     ArrayList<VehicleEntity> getAvailableVehicles(Session session) throws Exception;
     int makeVehicleOn(int id,Session session,Transaction transaction) throws Exception;
}
