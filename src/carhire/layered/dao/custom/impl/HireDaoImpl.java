package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.HireDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class HireDaoImpl implements HireDao {
    @Override
    public HireEntity get(Integer id, Session session) throws Exception {
        return (HireEntity) CrudUtil.getUniqueResult("From HireEntity Where hireId=?1",session,id);
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
    public int delete(Integer id, Session session) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<HireEntity> getAll(Session session) throws Exception {
        List<Object> result = CrudUtil.getListResult("From HireEntity",session);
        ArrayList<HireEntity> hireEntities = new ArrayList<>();
        for (Object o:result){
            hireEntities.add((HireEntity) o);
        }
        return hireEntities;
    }
}
