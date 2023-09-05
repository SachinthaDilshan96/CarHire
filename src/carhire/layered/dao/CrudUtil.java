package carhire.layered.dao;

import carhire.layered.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CrudUtil {

    private static Query getQuery(String query,Session session,Object...args){
        Query q = session.createQuery(query);
        if (args!=null){
           for (int i=0;i<args.length;i++){
                q.setParameter((i+1),args[i]);
            }
        }
        return q;
    }

    public static int executeUpdate(String sql,Session session, Object...args){
        Query query = getQuery(sql,session,args);
        Transaction transaction = session.beginTransaction();
        try{
            int i = query.executeUpdate();
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }

    public static Object getUniqueResult(String sql,Session session, Object...args){
        Query query = getQuery(sql,session,args);
        return query.uniqueResult();
    }

    public static List<Object> getListResult(String sql, Session session, Object...args){
        Query query = getQuery(sql,session,args);
        return query.list();
    }

    public static int save(Object object,Session session){
        Transaction transaction = session.beginTransaction();
        try {
            int i = (int)session.save(object);
            transaction.commit();
            return i;
        }catch (Exception e){
            transaction.rollback();
            return -1;
        }
    }
}
