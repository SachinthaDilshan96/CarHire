package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.CustomerDao;
import carhire.layered.dao.custom.HireDao;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.dao.custom.VehicleDao;
import carhire.layered.dto.HireDto;
import carhire.layered.entity.CustomerEntity;
import carhire.layered.entity.HireEntity;
import carhire.layered.entity.UserEntity;
import carhire.layered.entity.VehicleEntity;
import carhire.layered.service.custom.HireService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HireServiceImpl implements HireService {
    VehicleDao vehicleDao = (VehicleDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE);
    HireDao hireDao = (HireDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.HIRE);
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    Session session  = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public int addHire(HireDto hireDto) throws Exception {
        VehicleEntity vehicleEntity = vehicleDao.getVehicleByID(hireDto.getVehicleId(),session);
        CustomerEntity customerEntity = customerDao.getByID(hireDto.getCustomerId(),session);
        UserEntity userEntity = userDao.getById(hireDto.getOrderPlacedBy(),session);
        if (vehicleEntity!=null & customerEntity!=null & userEntity!=null){
            System.out.println("Beofre the transaction");
            Transaction transaction = session.beginTransaction();
            System.out.println("after the transaction"+transaction.getStatus());
            HireEntity hireEntity = new HireEntity();
            hireEntity.setVehicleEntity(vehicleEntity);
            hireEntity.setCustomerEntity(customerEntity);
            hireEntity.setUserEntity(userEntity);
            hireEntity.setFromDate(hireDto.getFromDate());
            hireEntity.setToDate(hireDto.getToDate());
            hireEntity.setReturned(false);
            hireEntity.setTotal(hireDto.getTotal());
            hireEntity.setDailyRental(hireDto.getDailyRental());
            hireEntity.setAdvance(hireDto.getAdvance());
            hireEntity.setDeposit(hireDto.getDeposit());
            hireEntity.setBalance(hireDto.getBalance());

            System.out.println("before the add"+hireEntity.getHireId());
            int i = hireDao.add(hireEntity,session);
            System.out.println("after add "+i+"");
            if (i>=0){
                hireEntity.setHireId(i);
                boolean isVehicleUpdated = true;
                List<HireEntity> hireEntities = vehicleEntity.getHireEntities();
                hireEntities.add(hireEntity);
                vehicleEntity.setHireEntities(hireEntities);
                int makeVehicleOnResult =vehicleDao.makeVehicleOn(vehicleEntity.getVehicleId(),session);
                if (makeVehicleOnResult<0){
                    isVehicleUpdated = false;
                }
                if (isVehicleUpdated){
                    transaction.commit();
                    session.close();
                    return 1;
                }else {
                    transaction.rollback();
                    session.close();
                    return -1;
                }
            }else {
                transaction.rollback();
                session.close();
                return -1;
            }
        }else {
            return -1;
        }
    }
}
