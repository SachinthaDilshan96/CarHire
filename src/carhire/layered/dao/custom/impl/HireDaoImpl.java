package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.HireDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HireDaoImpl implements HireDao {
    @Override
    public HireEntity get(Integer id, Session session) throws Exception {
        return (HireEntity) CrudUtil.getUniqueResult("From HireEntity Where hireId=?1",session,id);
    }

    @Override
    public int add(HireEntity hireEntity, Session session, Transaction transaction) throws Exception {
        return CrudUtil.save(hireEntity,session,transaction);
    }

    @Override
    public int update(HireEntity hireEntity, Session session,Transaction transaction) throws Exception {
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

    @Override
    public ArrayList<HireEntity> getAllHiresToBeReturned(Session session) throws Exception {
        List<Object> result = CrudUtil.getListResult("From HireEntity Where isReturned=0",session);
        ArrayList<HireEntity> hireEntities = new ArrayList<>();
        for (Object o:result){
            hireEntities.add((HireEntity) o);
        }
        return hireEntities;
    }

    @Override
    public int markAsReturned(HireEntity hireEntity, Session session,Transaction transaction) throws Exception {
        return CrudUtil.executeUpdate("Update HireEntity Set balance=0, isReturned=1 Where hireId=?1",session,transaction,hireEntity.getHireId());
    }

    @Override
    public ArrayList<HireEntity> getAllOverDueHires(Session session,LocalDate localDate) throws Exception {
        List<Object> result = CrudUtil.getListResult("From HireEntity Where toDate<=?1 AND isReturned=0",session,Date.valueOf(localDate));
        ArrayList<HireEntity> hireEntities = new ArrayList<>();
        for (Object o:result){
            hireEntities.add((HireEntity) o);
        }
        return hireEntities;
    }
}
