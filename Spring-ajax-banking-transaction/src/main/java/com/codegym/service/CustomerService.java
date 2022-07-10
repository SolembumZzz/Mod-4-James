package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.model.Withdraw;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.model.dto.TransferDTO;
import com.codegym.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService implements ICustomerService{
    @Autowired
    ICustomerRepository customerRepo;

    @Override
    public List<CustomerDTO> selectAllCustomersDTO() {
        return customerRepo.selectAllCustomersDTO();
    }

    @Override
    public Customer selectCustomerById(Long id) {
        Customer customer;
        try {
            customer = customerRepo.findById(id).get();
        } catch (NoSuchElementException nsee) {
            return null;
        }
        return customer;
    }

    @Override
    public List<CustomerDTO> selectAllRecipients(Long id) {
        return null;
    }

    @Override
    public CustomerDTO selectCustomerDTOById(Long id) {
        return customerRepo.selectCustomerDTO(id);
    }

    @Override
    public boolean ifExists(Long id) {
        return customerRepo.existsById(id);
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        return customerRepo.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepo.save(updatedCustomer);
    }

    @Override
    public void suspendCustomerById(Long id) {
        Customer customer = customerRepo.findById(id).get();
        customer.setDeleted(true);
        customerRepo.save(customer);
    }

    @Override
    public boolean ifSuspended(Long id) {
        return customerRepo.existsByIdAndDeletedTrue(id);
    }

    @Override
    public boolean ifEmailExists(String email) {
        return customerRepo.existsByEmail(email);
    }

    @Override
    public boolean ifEmailExistsExceptSelf(String email, Long id) {
        return customerRepo.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public boolean ifPhoneExists(String phone) {
        return customerRepo.existsByPhone(phone);
    }

    @Override
    public boolean ifPhoneExistsExceptSelf(String phone, Long id) {
        return customerRepo.existsByPhoneAndIdIsNot(phone, id);
    }

    @Override
    public boolean deposit(Deposit deposit) {
        Long id = deposit.getCustomer().getId();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        return customerRepo.deposit(id, transactionAmount);
    }

    @Override
    public boolean withdraw(Withdraw withdraw) {
        Long id = withdraw.getCustomer().getId();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        return customerRepo.withdraw(id, transactionAmount);
    }

    @Override
    public boolean transfer(Transfer transfer) {
        Long senderId = transfer.getSender().getId();
        Long recipientId = transfer.getRecipient().getId();
        BigDecimal fee = transfer.getFee();
        BigDecimal transactionAmount = transfer.getTransactionAmount();
        return customerRepo.transfer(senderId, recipientId, fee, transactionAmount);
    }

    @Override
    public boolean transferByDTO(TransferDTO transferDTO) {
        return false;
    }

    @Override
    public List<CustomerDTO> selectRecipientsDTO(Long senderId) {
        return customerRepo.selectAllRecipientsDTO(senderId);
    }

    @Override
    public List<Transfer> selectAllTransfers() {
        return null;
    }
}

