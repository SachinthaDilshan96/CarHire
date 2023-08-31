package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudDao;
import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.entity.UserEntity;
import org.hibernate.Session;

import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    @Override
    public UserEntity get(String s, Session session) throws Exception {
        return CrudUtil.getUser("FROM UserEntity",s,session);
    }

    @Override
    public int add(UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.addUser(userEntity,session);
    }
}
