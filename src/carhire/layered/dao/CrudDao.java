package carhire.layered.dao;

public interface CrudDao <T,ID,S> extends SuperDao{
    T get(ID id, S s,boolean a) throws Exception;
}
