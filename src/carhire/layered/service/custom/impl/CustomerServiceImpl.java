package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.CustomerDao;
import carhire.layered.dto.CustomerDto;
import carhire.layered.dto.Embedded.Name;
import carhire.layered.entity.CustomerEntity;
import carhire.layered.entity.embeddeb.CustomerName;
import carhire.layered.service.custom.CustomerService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public int addCustomer(CustomerDto customerDto) throws Exception {
        return customerDao.add(new CustomerEntity(
                customerDto.getCustomerId(),
                customerDto.getNic(),
                new CustomerName(customerDto.getName().getFirstName(),customerDto.getName().getLastName()),
                customerDto.getAddress(),
                customerDto.getMobileNumber()),session);
    }

    @Override
    public int updateCustomer(CustomerDto customerDto) throws Exception {
        return customerDao.update(new CustomerEntity(
                customerDto.getCustomerId(),
                customerDto.getNic(),
                new CustomerName(customerDto.getName().getFirstName(),customerDto.getName().getLastName()),
                customerDto.getAddress(),
                customerDto.getMobileNumber()),session);
    }

    @Override
    public CustomerDto getCustomer(String nic) throws Exception {
        CustomerEntity customerEntity = customerDao.get(nic,session);
        return new CustomerDto(
                customerEntity.getCustomerid(),
                customerEntity.getNic(),
                new Name(customerEntity.getCustomerName().getFirstName(),customerEntity.getCustomerName().getLastName()),
                customerEntity.getAddress(),
                customerEntity.getMobile());
    }

    @Override
    public int deleteCustomer(int id) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws Exception {
        ArrayList<CustomerEntity> customerEntities = customerDao.getAll(session);
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        for(CustomerEntity customerEntity:customerEntities){
            customerDtos.add(new CustomerDto(
                    customerEntity.getCustomerid(),
                    customerEntity.getNic(),
                    new Name(customerEntity.getCustomerName().getFirstName(),customerEntity.getCustomerName().getLastName()),
                    customerEntity.getAddress(),
                    customerEntity.getMobile()
            ));
        }
        return customerDtos;
    }
}
