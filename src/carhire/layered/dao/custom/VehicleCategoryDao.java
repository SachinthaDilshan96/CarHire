package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.VehicleCategoryEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public interface VehicleCategoryDao extends CrudDao<VehicleCategoryEntity,String, Session> {
    int add(VehicleCategoryEntity vehicleCategoryEntity) throws Exception;
    int update(VehicleCategoryEntity vehicleCategoryEntity) throws Exception;
    ArrayList<VehicleCategoryEntity> getAll() throws Exception;
}
