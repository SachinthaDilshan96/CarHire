package carhire.layered.service.custom.impl;

import carhire.layered.dto.CustomerDto;
import carhire.layered.service.custom.CustomerService;

import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public int addCustomer(CustomerDto customerDto) throws Exception {
        return 0;
    }

    @Override
    public int updateCustomer(CustomerDto customerDto) throws Exception {
        return 0;
    }

    @Override
    public CustomerDto getCustomer(String nic) throws Exception {
        return null;
    }

    @Override
    public int deleteCustomer(int id) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws Exception {
        return null;
    }
}
