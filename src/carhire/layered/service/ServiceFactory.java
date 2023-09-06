package carhire.layered.service;

import carhire.layered.service.custom.impl.UserServiceImpl;
import carhire.layered.service.custom.impl.VehicleCategoryServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstance(){
        return (serviceFactory==null)?serviceFactory=new ServiceFactory():serviceFactory;
    }

    public SuperService getService(ServiceType serviceType){
        switch (serviceType){
            case USER:
                return new UserServiceImpl();
            case VEHICLE_CATEGORY:
                return new VehicleCategoryServiceImpl();
            default:
                return null;
        }
    }

    public enum ServiceType{
        USER,VEHICLE_CATEGORY
    }
}
