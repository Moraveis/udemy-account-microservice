package course.udemy.springboot.accounts.service.impl;

import course.udemy.springboot.accounts.constants.AccountsConstants;
import course.udemy.springboot.accounts.dto.AccountDto;
import course.udemy.springboot.accounts.dto.AccountMessageDto;
import course.udemy.springboot.accounts.dto.CustomerDto;
import course.udemy.springboot.accounts.entity.Account;
import course.udemy.springboot.accounts.entity.Customer;
import course.udemy.springboot.accounts.exception.CustomerAlreadyExistsException;
import course.udemy.springboot.accounts.exception.ResourceNotFoundException;
import course.udemy.springboot.accounts.mapper.AccountMapper;
import course.udemy.springboot.accounts.mapper.CustomerMapper;
import course.udemy.springboot.accounts.repositories.AccountRepository;
import course.udemy.springboot.accounts.repositories.CustomerRepository;
import course.udemy.springboot.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber = " + customerDto.getMobileNumber());
        }

        Customer saved = customerRepository.save(customer);
        Account savedAccount = accountRepository.save(createNewAccount(saved));
        sendCommunication(savedAccount, saved);
    }

    private void sendCommunication(Account account, Customer customer) {
        var accountMessageDto = new AccountMessageDto(account.getAccountNumber(),
                customer.getName(), customer.getEmail(), customer.getMobileNumber());

        log.info("Sending communication request for the details: {}", accountMessageDto);
        var result = streamBridge.send("sendCommunication-out-0", accountMessageDto);
        log.info("Is the Communication request successfully processed? : {}", result);
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountDto accountsDto = customerDto.getAccountDto();

        if (accountsDto != null) {
            Account accounts = accountRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));
            AccountMapper.mapToAccount(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if (accountNumber != null) {
            Account account = accountRepository.findById(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString()));
            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return isUpdated;
    }

}
