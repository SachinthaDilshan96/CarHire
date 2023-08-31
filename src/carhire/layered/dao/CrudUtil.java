package carhire.layered.dao;

import carhire.layered.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CrudUtil {

    public static UserEntity getUser(String hql, String email, Session session){
        Query query = session.createQuery(hql);
        //query.setParameter("email",email);
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
}
