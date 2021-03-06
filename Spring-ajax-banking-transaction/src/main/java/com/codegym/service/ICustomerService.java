package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.model.Withdraw;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.model.dto.RecipientDTO;
import com.codegym.model.dto.TransferDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<CustomerDTO> selectAllCustomersDTO();

    Customer selectCustomerById(Long id);

    List<CustomerDTO> selectAllRecipients(Long id);

    CustomerDTO selectCustomerDTOById(Long id);

    boolean ifExists(Long id);

    Customer createCustomer(Customer newCustomer);

    Customer updateCustomer(Customer updatedCustomer);

    void suspendCustomerById(Long id);

    boolean ifEmailExists(String email);

    boolean ifEmailExistsExceptSelf(String email, Long id);

    boolean ifPhoneExists(String phone);

    boolean ifPhoneExistsExceptSelf(String phone, Long id);

    boolean ifSuspended(Long id);

    boolean deposit(Deposit deposit);

    boolean withdraw(Withdraw withdraw);

    boolean transfer(Transfer transfer);

    boolean transferByDTO(TransferDTO transferDTO);

    List<CustomerDTO> selectRecipientsDTO(Long senderId);

    List<Transfer> selectAllTransfers();
}
