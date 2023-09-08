package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleEntity;
import org.hibernate.Session;

public interface VehicleDao extends CrudDao<VehicleEntity,String, Session> {
}
