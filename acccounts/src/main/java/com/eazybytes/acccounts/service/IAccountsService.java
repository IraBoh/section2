package com.eazybytes.acccounts.service;

import com.eazybytes.acccounts.dto.CustomerDto;

public interface IAccountsService {


    /**
     * @param customerDto-CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNUmber - Input mobileNumber
     * @return Account Details based on a given mobileNumber
     */
    CustomerDto fetchAccount(String mobileNUmber);

    /**
     * @param customerDto
     * @retur boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * @param - Input mobileNumber
     * @return boolean indicating if the delete of the account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);

}
