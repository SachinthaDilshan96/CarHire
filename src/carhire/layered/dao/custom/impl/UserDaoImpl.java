package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudDao;
import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.entity.UserEntity;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    @Override
    public UserEntity get(String s, Session session, boolean isAll) throws Exception {
        if (isAll){
            return CrudUtil.getUser("FROM UserEntity where email=:email",s,session);
        }else{
            return CrudUtil.getUser("FROM UserEntity where email=:email and status='in'",s,session);
        }
    }

    @Override
    public int add(UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.addUser(userEntity,session);
    }

    @Override
    public int update(String s,UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.updateUser(userEntity,session);
    }

    @Override
    public int delete(int id , Session session) throws Exception {
        return CrudUtil.deleteUser(id, session);
    }

    @Override
    public ArrayList<UserEntity> getAll(Session session) throws Exception {
        return (ArrayList<UserEntity>) CrudUtil.getAllUsers(session);
    }

    @Override
    public int updateProfile(UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.updateProfile("",userEntity,session);
    }
}
