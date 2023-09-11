package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.CustomerDao;
import carhire.layered.entity.CustomerEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public CustomerEntity get(String nic, Session session) throws Exception {
        return (CustomerEntity) CrudUtil.getUniqueResult("FROM CustomerEntity WHERE nic = ?1",session,nic);
    }

    @Override
    public int add(CustomerEntity customerEntity, Session session) throws Exception {
        return CrudUtil.save(customerEntity,session);
    }

    @Override
    public int update(CustomerEntity customerEntity, Session session) throws Exception {
        return 0;
    }

    @Override
    public int delete(String nic, Session session) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<CustomerEntity> getAll(Session session) throws Exception {
        List<Object> result = CrudUtil.getListResult("From CustomerEntity",session);
        ArrayList<CustomerEntity> customerEntities = new ArrayList<>();
        for (Object o: result) {
            customerEntities.add((CustomerEntity)o);
        }
        return customerEntities;
    }

    @Override
    public int deleteByID(int id) throws Exception {
        return 0;
    }
}
