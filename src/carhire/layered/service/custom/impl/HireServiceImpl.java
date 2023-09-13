package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.CustomerDao;
import carhire.layered.dao.custom.HireDao;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.dao.custom.VehicleDao;
import carhire.layered.dto.CustomerDto;
import carhire.layered.dto.Embedded.Name;
import carhire.layered.dto.HireDto;
import carhire.layered.dto.UserDto;
import carhire.layered.dto.VehicleDto;
import carhire.layered.entity.CustomerEntity;
import carhire.layered.entity.HireEntity;
import carhire.layered.entity.UserEntity;
import carhire.layered.entity.VehicleEntity;
import carhire.layered.service.custom.HireService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HireServiceImpl implements HireService {
    VehicleDao vehicleDao = (VehicleDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.VEHICLE);
    HireDao hireDao = (HireDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.HIRE);
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    Session session  = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public int addHire(HireDto hireDto) throws Exception {
        VehicleEntity vehicleEntity = vehicleDao.getVehicleByID(hireDto.getVehicle().getVehicleId(),session);
        CustomerEntity customerEntity = customerDao.getByID(hireDto.getCustomer().getCustomerId(),session);
        UserEntity userEntity = userDao.getById(hireDto.getOrderPlacedBy().getId(), session);
        if (vehicleEntity!=null & customerEntity!=null & userEntity!=null){
           // Transaction transaction = session.beginTransaction();

            HireEntity hireEntity = new HireEntity();
            hireEntity.setVehicleEntity(vehicleEntity);
            hireEntity.setCustomerEntity(customerEntity);
            hireEntity.setUserEntity(userEntity);
            hireEntity.setFromDate(hireDto.getFromDate());
            hireEntity.setToDate(hireDto.getToDate());
            hireEntity.setReturned(false);
            hireEntity.setTotal(hireDto.getTotal());
            hireEntity.setDailyRental(hireDto.getDailyRental());
            hireEntity.setDeposit(hireDto.getDeposit());
            hireEntity.setAdvance(hireDto.getAdvance());
            hireEntity.setBalance(hireDto.getBalance());

            int i = hireDao.add(hireEntity,session);
            System.out.println("this is i "+i);
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
                   // transaction.commit();
                   // session.close();
                    return 1;
                }else {
                   // transaction.rollback();
                    //session.close();
                    return -1;
                }
            }else {
               // transaction.rollback();
                //session.close();
                return -1;
            }
        }else {
            return -1;
        }
    }

    @Override
    public HireDto getHire(int id) throws Exception {
        HireEntity hireEntity = hireDao.get(id,session);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleId(hireEntity.getVehicleEntity().getVehicleId());
        vehicleDto.setVehicleNumber(hireEntity.getVehicleEntity().getVehicleNumber());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(hireEntity.getCustomerEntity().getCustomerid());
        customerDto.setName(new Name(hireEntity.getCustomerEntity().getCustomerName().getFirstName(), hireEntity.getCustomerEntity().getCustomerName().getLastName()));

        UserDto userDto = new UserDto();
        userDto.setId(hireEntity.getUserEntity().getUserId());
        userDto.setFirstName(hireEntity.getUserEntity().getFirstName());
        userDto.setLastName(hireEntity.getUserEntity().getLastName());

        return new HireDto(
                hireEntity.getHireId(),
                vehicleDto,
                customerDto,
                userDto,
                hireEntity.getFromDate(),
                hireEntity.getToDate(),
                hireEntity.isReturned()?"Returned":"Not Returned",
                hireEntity.getTotal(),
                hireEntity.getDailyRental(),
                hireEntity.getDeposit(),
                hireEntity.getAdvance(),
                hireEntity.getBalance()
        );
    }

    @Override
    public ArrayList<HireDto> getAllHires() throws Exception {
        ArrayList<HireEntity> hireEntities = hireDao.getAll(session);
        ArrayList<HireDto> hireDtos = new ArrayList<>();
        for (HireEntity hireEntity:hireEntities){
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleId(hireEntity.getVehicleEntity().getVehicleId());
            vehicleDto.setVehicleNumber(hireEntity.getVehicleEntity().getVehicleNumber());

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(hireEntity.getCustomerEntity().getCustomerid());
            customerDto.setName(new Name(hireEntity.getCustomerEntity().getCustomerName().getFirstName(), hireEntity.getCustomerEntity().getCustomerName().getLastName()));

            UserDto userDto = new UserDto();
            userDto.setId(hireEntity.getUserEntity().getUserId());
            userDto.setFirstName(hireEntity.getUserEntity().getFirstName());
            userDto.setLastName(hireEntity.getUserEntity().getLastName());
            hireDtos.add(new HireDto(
                    hireEntity.getHireId(),
                    vehicleDto,
                    customerDto,
                    userDto,
                    hireEntity.getFromDate(),
                    hireEntity.getToDate(),
                    hireEntity.isReturned()?"Returned":"Not Returned",
                    hireEntity.getTotal(),
                    hireEntity.getDailyRental(),
                    hireEntity.getDeposit(),
                    hireEntity.getAdvance(),
                    hireEntity.getBalance()
            ));
        }
        return hireDtos;
    }

    @Override
    public ArrayList<HireDto> getAllOverdueHires(LocalDate localDate) throws Exception {
        ArrayList<HireEntity> hireEntities = hireDao.getAllOverDueHires(session, localDate);
        ArrayList<HireDto> hireDtos = new ArrayList<>();
        for (HireEntity hireEntity:hireEntities){
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleId(hireEntity.getVehicleEntity().getVehicleId());
            vehicleDto.setVehicleNumber(hireEntity.getVehicleEntity().getVehicleNumber());

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(hireEntity.getCustomerEntity().getCustomerid());
            customerDto.setName(new Name(hireEntity.getCustomerEntity().getCustomerName().getFirstName(), hireEntity.getCustomerEntity().getCustomerName().getLastName()));

            UserDto userDto = new UserDto();
            userDto.setId(hireEntity.getUserEntity().getUserId());
            userDto.setFirstName(hireEntity.getUserEntity().getFirstName());
            userDto.setLastName(hireEntity.getUserEntity().getLastName());
            hireDtos.add(new HireDto(
                    hireEntity.getHireId(),
                    vehicleDto,
                    customerDto,
                    userDto,
                    hireEntity.getFromDate(),
                    hireEntity.getToDate(),
                    hireEntity.isReturned()?"Returned":"Not Returned",
                    hireEntity.getTotal(),
                    hireEntity.getDailyRental(),
                    hireEntity.getDeposit(),
                    hireEntity.getAdvance(),
                    hireEntity.getBalance()
            ));
        }
        return hireDtos;

    }

    @Override
    public ArrayList<HireDto> getAllHiresToBeReturned() throws Exception {
        ArrayList<HireEntity> hireEntities = hireDao.getAllHiresToBeReturned(session);
        ArrayList<HireDto> hireDtos = new ArrayList<>();
        for (HireEntity hireEntity:hireEntities){
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleId(hireEntity.getVehicleEntity().getVehicleId());
            vehicleDto.setVehicleNumber(hireEntity.getVehicleEntity().getVehicleNumber());

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(hireEntity.getCustomerEntity().getCustomerid());
            customerDto.setName(new Name(hireEntity.getCustomerEntity().getCustomerName().getFirstName(), hireEntity.getCustomerEntity().getCustomerName().getLastName()));

            UserDto userDto = new UserDto();
            userDto.setId(hireEntity.getUserEntity().getUserId());
            userDto.setFirstName(hireEntity.getUserEntity().getFirstName());
            userDto.setLastName(hireEntity.getUserEntity().getLastName());
            hireDtos.add(new HireDto(
                    hireEntity.getHireId(),
                    vehicleDto,
                    customerDto,
                    userDto,
                    hireEntity.getFromDate(),
                    hireEntity.getToDate(),
                    hireEntity.isReturned()?"Returned":"Not Returned",
                    hireEntity.getTotal(),
                    hireEntity.getDailyRental(),
                    hireEntity.getDeposit(),
                    hireEntity.getAdvance(),
                    hireEntity.getBalance()
            ));
        }
        return hireDtos;
    }

    @Override
    public int markAsReturned(HireDto hireDto) throws Exception {
        HireEntity hireEntity = new HireEntity();
        hireEntity.setHireId(hireDto.getHireId());
        return hireDao.markAsReturned(hireEntity,session);
    }


}
