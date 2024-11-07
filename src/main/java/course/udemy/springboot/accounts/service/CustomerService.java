package course.udemy.springboot.accounts.service;

import course.udemy.springboot.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
