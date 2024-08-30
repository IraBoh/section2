package com.eazybytes.acccounts.repository;

import com.eazybytes.acccounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByMobileNumber(String mobileNumber);
}
