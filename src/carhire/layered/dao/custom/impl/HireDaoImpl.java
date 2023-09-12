package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.HireDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public class HireDaoImpl implements HireDao {
    @Override
    public HireEntity get(String s, Session session) throws Exception {
        return null;
    }

    @Override
    public int add(HireEntity hireEntity, Session session) throws Exception {
        System.out.println("Daily rental "+hireEntity.getDailyRental());
        return CrudUtil.save(hireEntity,session);
    }

    @Override
    public int update(HireEntity hireEntity, Session session) throws Exception {
        return 0;
    }

    @Override
    public int delete(String s, Session session) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<HireEntity> getAll(Session session) throws Exception {
        return null;
    }
}
