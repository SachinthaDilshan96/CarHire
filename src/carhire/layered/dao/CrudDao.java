package carhire.layered.dao;

import java.util.ArrayList;
import java.util.List;

public interface CrudDao <T,ID,S> extends SuperDao{
    //T get(ID id, S s,boolean a) throws Exception;
    T get(ID id, S s) throws Exception;
    int add(T t, S s) throws Exception;
    int update (T t, S s) throws Exception;
    int delete(ID id, S s) throws Exception;
    ArrayList<T> getAll(S s) throws Exception;
}
