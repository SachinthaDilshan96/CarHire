package carhire.layered.service;

import carhire.layered.service.custom.impl.UserServiceImpl;

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
            default:
                return null;
        }
    }

    public enum ServiceType{
        USER,
    }
}
