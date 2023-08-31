package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.UserEntity;
import org.hibernate.Session;

public interface UserDao extends CrudDao<UserEntity,String,Session> {
    UserEntity get(String s, Session session) throws Exception;
    int add(UserEntity userEntity, Session session) throws Exception;
}
