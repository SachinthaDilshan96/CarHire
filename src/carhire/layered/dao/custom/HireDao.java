package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;

import java.util.ArrayList;

public interface HireDao extends CrudDao<HireEntity,Integer, Session> {
        ArrayList<HireEntity> getAllHiresToBeReturned(Session session) throws Exception;
        int markAsReturned(HireEntity hireEntity, Session session)throws Exception;
}
