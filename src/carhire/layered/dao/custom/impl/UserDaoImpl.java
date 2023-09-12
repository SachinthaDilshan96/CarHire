package carhire.layered.dao.custom.impl;

import carhire.layered.dao.CrudUtil;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.dto.UserDto;
import carhire.layered.entity.UserEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public UserEntity get(String email, Session session, boolean isAll) throws Exception {
        if (isAll){
            return (UserEntity) CrudUtil.getUniqueResult("FROM UserEntity where email=?1",session,email);
        }else{
            return (UserEntity) CrudUtil.getUniqueResult("FROM UserEntity where email=?1 and status=?2",session,email,"in");
        }
    }

    @Override
    public UserEntity get(String s, Session session) throws Exception {
        return null;
    }

    @Override
    public int add(UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.save(userEntity,session);
    }

    @Override
    public int update(UserEntity userEntity, Session session) throws Exception {
        return CrudUtil.executeUpdate(
                "update UserEntity set firstName=?1, lastName=?2,level=?3,status=?4 where userId=?5",
                session,
                userEntity.getFirstName(),userEntity.getLastName(),userEntity.getLevel(),userEntity.getStatus(),userEntity.getUserId());
    }

    @Override
    public int delete(String s, Session session) throws Exception {
        return 0;
    }

    @Override
    public int delete(int id , Session session) throws Exception {
        return CrudUtil.executeUpdate(
                "update UserEntity set status=?1 where userId=?2",
                session,
                "out",id);
    }

    @Override
    public ArrayList<UserEntity> getAll(Session session) throws Exception {
        List<Object> result= CrudUtil.getListResult("FROM UserEntity",session);
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        for (Object u:result) {
            UserEntity userEntity = (UserEntity)u;
            userEntities.add(userEntity);
        }
        return userEntities;
    }

    @Override
    public int updateProfile(UserEntity userEntity, Session session) throws Exception {
        //return CrudUtil.updateProfile("",userEntity,session);
        return CrudUtil.executeUpdate(
                "update UserEntity set firstName=?1, lastName=?2, password=?3 where userId=?4",
                session,
                userEntity.getFirstName(),userEntity.getLastName(),userEntity.getPassword(),userEntity.getUserId());
    }

    @Override
    public UserEntity getById(int id, Session session) throws Exception {
        return session.get(UserEntity.class,id);
    }
}
