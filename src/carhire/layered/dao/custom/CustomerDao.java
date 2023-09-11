package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.CustomerEntity;
import org.hibernate.Session;

public interface CustomerDao extends CrudDao<CustomerEntity,String, Session> {
    int deleteByID(int id) throws Exception;
}
