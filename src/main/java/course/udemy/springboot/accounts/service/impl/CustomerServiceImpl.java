package course.udemy.springboot.accounts.service.impl;

import course.udemy.springboot.accounts.dto.AccountDto;
import course.udemy.springboot.accounts.dto.CardDto;
import course.udemy.springboot.accounts.dto.CustomerDetailsDto;
import course.udemy.springboot.accounts.dto.LoanDto;
import course.udemy.springboot.accounts.entity.Account;
import course.udemy.springboot.accounts.entity.Customer;
import course.udemy.springboot.accounts.exception.ResourceNotFoundException;
import course.udemy.springboot.accounts.mapper.AccountMapper;
import course.udemy.springboot.accounts.mapper.CustomerMapper;
import course.udemy.springboot.accounts.repositories.AccountRepository;
import course.udemy.springboot.accounts.repositories.CustomerRepository;
import course.udemy.springboot.accounts.service.CustomerService;
import course.udemy.springboot.accounts.service.client.CardsFeignClient;
import course.udemy.springboot.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsClient;
    private final LoansFeignClient loansClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        ResponseEntity<LoanDto> loanResponse = loansClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoanDto(loanResponse.getBody());

        ResponseEntity<CardDto> cardResponse = cardsClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardDto(cardResponse.getBody());

        return customerDetailsDto;
    }
}
