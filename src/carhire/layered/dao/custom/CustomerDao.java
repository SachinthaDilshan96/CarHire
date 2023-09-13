package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public interface CustomerDao extends CrudDao<CustomerEntity,String, Session, Transaction> {
    int deleteByID(int id) throws Exception;
    CustomerEntity getByID(int id, Session session) throws Exception;
}
