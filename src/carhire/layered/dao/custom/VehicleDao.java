package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public interface VehicleDao extends CrudDao<VehicleEntity,String, Session> {
    int delete(Integer id, Session session) throws Exception;

     VehicleEntity getVehicleByID(int vehicleID, Session session) throws Exception;
     int makeVehicleIn(int id, Session session) throws Exception;
     ArrayList<VehicleEntity> getAvailableVehicles(Session session) throws Exception;
     int makeVehicleOn(int id,Session session) throws Exception;
}
