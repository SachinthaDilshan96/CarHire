package carhire.layered.dao;

import carhire.layered.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CrudUtil {

    public static UserEntity getUser(String hql, String email, Session session){
        Query query = session.createQuery(hql);
        query.setParameter("email",email);
        return (UserEntity)query.uniqueResult();
    }

    public static int addUser(UserEntity userEntity,Session session){
        Transaction transaction = session.beginTransaction();
        try {
            int i = (int) session.save(userEntity);
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }

    public static int updateUser(UserEntity userEntity, Session session){
        Transaction transaction = session.beginTransaction();
        Query q=session.createQuery("update UserEntity set firstName=:firstName, lastName=:lastName,level=:level,status=:status where userId=:userId");
        q.setParameter("firstName",userEntity.getFirstName());
        q.setParameter("lastName",userEntity.getLastName());
        q.setParameter("level",userEntity.getLevel());
        q.setParameter("userId",userEntity.getUserId());
        q.setParameter("status",userEntity.getStatus());
        try {
            int i = q.executeUpdate();
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }

    public static int updateProfile(String s,UserEntity userEntity, Session session) throws Exception{
        Transaction transaction = session.beginTransaction();
        Query q=session.createQuery("update UserEntity set firstName=:firstName, lastName=:lastName, password=:password where userId=:userId");
        q.setParameter("firstName",userEntity.getFirstName());
        q.setParameter("lastName",userEntity.getLastName());
        q.setParameter("password",userEntity.getPassword());
        q.setParameter("userId",userEntity.getUserId());
        try {
            int i = q.executeUpdate();
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }

    public static int deleteUser(int id, Session session){
        Transaction transaction = session.beginTransaction();
        Query q=session.createQuery("update UserEntity set status=:status where userId=:userId");
        q.setParameter("status","out");
        q.setParameter("userId",id);
        try {
            int i = q.executeUpdate();
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }

    public static List<UserEntity> getAllUsers(Session session){
        String hql = "FROM UserEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
}
