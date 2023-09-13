package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public interface HireDao extends CrudDao<HireEntity,Integer, Session, Transaction > {
        ArrayList<HireEntity> getAllHiresToBeReturned(Session session) throws Exception;
        int markAsReturned(HireEntity hireEntity, Session session,Transaction transaction)throws Exception;
        ArrayList<HireEntity> getAllOverDueHires(Session session, LocalDate localDate) throws Exception;
        int save(HireEntity hireEntity,Session session) throws Exception;
}
