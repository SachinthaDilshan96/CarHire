package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.HireEntity;
import org.hibernate.Session;

public interface HireDao extends CrudDao<HireEntity,String, Session> {
}
