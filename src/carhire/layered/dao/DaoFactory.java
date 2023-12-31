package carhire.layered.dao;

import carhire.layered.dao.custom.impl.*;
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
            case VEHICLE_BRAND:
                return new VehicleBrandDaoImpl();
            case VEHICLE:
                return new VehicleDaoImpl();
            case CUSTOMER:
                return new CustomerDaoImpl();
            case HIRE:
                return new HireDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoTypes{
        USER,VEHICLE_CATEGORY,VEHICLE_BRAND,VEHICLE,CUSTOMER,HIRE
    }
}
