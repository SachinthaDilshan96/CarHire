package carhire.layered.dao;

import carhire.layered.dao.custom.impl.UserDaoImpl;
import carhire.layered.dao.custom.impl.VehicleCategoryDaoImpl;
import carhire.layered.service.SuperService;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null)?daoFactory = new DaoFactory():daoFactory;
    }

    public SuperDao getDao(DaoTypes daoType){
        switch (daoType){
            case USER:
                return new UserDaoImpl();
            case VEHICLE_CATEGORY:
                return new VehicleCategoryDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoTypes{
        USER,VEHICLE_CATEGORY
    }
}
