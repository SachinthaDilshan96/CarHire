package carhire.layered.service.custom;

import carhire.layered.dto.CustomerDto;
import carhire.layered.service.SuperService;

import java.util.ArrayList;

public interface CustomerService extends SuperService {
    int addCustomer(CustomerDto customerDto) throws Exception;
    int updateCustomer(CustomerDto customerDto) throws Exception;
    CustomerDto getCustomer(String nic) throws Exception;
    int deleteCustomer(int id) throws Exception;
    ArrayList<CustomerDto> getAllCustomers() throws Exception;
}
