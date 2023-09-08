package carhire.layered.dao.custom;

import carhire.layered.dao.CrudDao;
import carhire.layered.entity.UserEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public interface UserDao extends CrudDao<UserEntity,String,Session> {
    UserEntity get(String s, Session session,boolean isAll) throws Exception;
    int add(UserEntity userEntity, Session session) throws Exception;
    int update(UserEntity userEntity, Session session) throws Exception;
    int delete(int id, Session session) throws Exception;
    ArrayList<UserEntity> getAll(Session session) throws Exception;
    int updateProfile(UserEntity userEntity, Session session) throws Exception;
}
