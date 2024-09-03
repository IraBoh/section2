package com.eazybytes.acccounts.service.impl;

import com.eazybytes.acccounts.constants.AccountsConstants;
import com.eazybytes.acccounts.dto.AccountsDto;
import com.eazybytes.acccounts.dto.CustomerDto;
import com.eazybytes.acccounts.entity.Accounts;
import com.eazybytes.acccounts.entity.Customer;
import com.eazybytes.acccounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.acccounts.exception.ResourceNotFoundException;
import com.eazybytes.acccounts.mapper.AccountsMapper;
import com.eazybytes.acccounts.mapper.CustomerMapper;
import com.eazybytes.acccounts.repository.AccountsRepository;
import com.eazybytes.acccounts.repository.CustomerRepository;
import com.eazybytes.acccounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto -CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNUmber - Input mobileNumber
     * @return Account Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNUmber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNUmber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNUmber)
        );
      Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto ;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated= false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        //Check accountDto exists, find account by accountNumber
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(()
                            -> new ResourceNotFoundException
                            ("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));

        //Mapping and saving happens here
        AccountsMapper.mapToAccounts(accountsDto, accounts);
        accountsRepository.save(accounts);
        //find customer, map it and save
            Customer customer = customerRepository.findById(accounts.getCustomerId())
                    .orElseThrow(()
                            -> new ResourceNotFoundException
                            ("Customer", "CustomerId", accounts.getCustomerId().toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated= true;
    }
        return isUpdated;
    }

    /**
     * @param mobileNumber@return boolean indicating if the delete of the account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
