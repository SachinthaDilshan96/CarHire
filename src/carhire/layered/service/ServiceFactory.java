package carhire.layered.service;

import carhire.layered.service.custom.impl.*;

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
            case VEHICLE_BRAND:
                return new VehicleBrandServiceImpl();
            case VEHICLE_SERVICE:
                return new VehicleServiceImpl();
            case CUSTOMER:
                return new CustomerServiceImpl();
            case HIRE:
                return new HireServiceImpl();
            default:
                return null;
        }
    }

    public enum ServiceType{
        USER,VEHICLE_CATEGORY,VEHICLE_BRAND,VEHICLE_SERVICE,CUSTOMER,HIRE
    }
}
