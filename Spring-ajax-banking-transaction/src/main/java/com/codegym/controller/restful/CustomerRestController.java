package com.codegym.controller.restful;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.model.Withdraw;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.model.dto.TransferDTO;
import com.codegym.service.ICustomerService;
import com.codegym.utility.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> showListPage() {
        List<CustomerDTO> customerList = customerService.selectAllCustomersDTO();
        if (customerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selectCustomer(@PathVariable("id") String rawId) {
        try {
            Long id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new NoSuchElementException();
            CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (NumberFormatException | NoSuchElementException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerDTO customerDTO) {
        Map<String, String> errors = new HashMap<>();

        customerDTO.setBalance(BigDecimal.ZERO);

        if (customerService.ifEmailExists(customerDTO.getEmail()))
            errors.put("email", "This email has already been registered");

        if (customerService.ifPhoneExists(customerDTO.getPhone()))
            errors.put("phone", "This phone has already been registered");

        if (!errors.isEmpty())
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

        try {
            Customer customer = customerDTO.toCustomer();
            customerDTO = customerService.createCustomer(customer).toCustomerDTO();
            return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> edit(@PathVariable("id") String rawId,
                                  @RequestBody CustomerDTO customerDTO) {
        Map<String, String> errors = new HashMap<>();
        Long id;

        try {
            id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new IOException();
            if (customerService.ifSuspended(id))
                return new ResponseEntity<>("Customer suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        customerDTO.setId(id);

        if (customerService.ifEmailExistsExceptSelf(customerDTO.getEmail(), id))
            errors.put("email", "This email has already been registered");
        if (customerService.ifPhoneExistsExceptSelf(customerDTO.getPhone(), id))
            errors.put("phone", "This number has already been registered");

        if (!errors.isEmpty())
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

        try {
            Customer customer = customerDTO.toCustomer();
            customerDTO = customerService.updateCustomer(customer).toCustomerDTO();
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable("id") String rawId,
                                     @RequestBody String transactionAmount) {
        Long id;
        BigDecimal depositAmount;

        try {
            id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new IOException();
            if (customerService.ifSuspended(id))
                return new ResponseEntity<>("Customer suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        Customer customer = customerService.selectCustomerById(id);

        try {
            depositAmount = new BigDecimal(transactionAmount);
            if (depositAmount.compareTo(BigDecimalUtils.FIFTY) < 0
                    || depositAmount.compareTo(BigDecimalUtils.ONE_BILLION) > 0)
                return new ResponseEntity<>("Transaction amount must be between 50 and 1.000.000.000", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>("Transaction amount invalid", HttpStatus.BAD_REQUEST);
        }

        try {
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);
            deposit.setTransactionAmount(depositAmount);

            boolean success = customerService.deposit(deposit);
            if (!success)
                throw new IOException();

            CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (IOException ioe) {
            return new ResponseEntity<>("Database Error", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable("id") String rawId,
                                      @RequestBody String transactionAmount) {
        Long id;
        BigDecimal withdrawAmount;

        try {
            id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new IOException();
            if (customerService.ifSuspended(id))
                return new ResponseEntity<>("Customer suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        Customer customer = customerService.selectCustomerById(id);

        try {
            withdrawAmount = new BigDecimal(transactionAmount);
            if (withdrawAmount.compareTo(BigDecimalUtils.FIFTY) < 0
                    || withdrawAmount.compareTo(BigDecimalUtils.ONE_BILLION) > 0)
                return new ResponseEntity<>("Transaction amount must be between 50 and 1.000.000.000", HttpStatus.BAD_REQUEST);
            if (withdrawAmount.compareTo(customer.getBalance()) > 0)
                return new ResponseEntity<>("Withdraw amount cannot exceed the current balance", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>("Transaction amount invalid", HttpStatus.BAD_REQUEST);
        }

        try {
            Withdraw withdraw = new Withdraw();
            withdraw.setCustomer(customer);
            withdraw.setTransactionAmount(withdrawAmount);

            boolean success = customerService.withdraw(withdraw);
            if (!success)
                throw new IOException();

            CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (IOException ioe) {
            return new ResponseEntity<>("Database Error", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}/transfer")
    public ResponseEntity<?> transfer(@PathVariable("id") String rawId,
                                      @RequestBody TransferDTO transferDTO) {
        Long senderId;
        Long recipientId;
        Transfer transfer;
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal transferAmount = BigDecimal.ZERO;
        Map<String, String> errors = new HashMap<>();

        try {
            senderId = Long.parseLong(rawId);
            if (!customerService.ifExists(senderId))
                throw new IOException();
            if (customerService.ifSuspended(senderId))
                return new ResponseEntity<>("Customer suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        Customer sender = customerService.selectCustomerById(senderId);

        try {
            recipientId = Long.parseLong(transferDTO.getRecipientId());
            if (!customerService.ifExists(recipientId))
                throw new IOException();
            if (customerService.ifSuspended(recipientId))
                return new ResponseEntity<>("Recipient suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        Customer recipient = customerService.selectCustomerById(recipientId);

        try {
            fee = new BigDecimal(transferDTO.getFee());
            if (fee.compareTo(BigDecimal.ZERO) < 0 || fee.compareTo(BigDecimalUtils.ONE_HUNDRED) > 0)
                errors.put("fee", "Fee must be between 0 and 100");
        } catch (NumberFormatException nfe) {
            errors.put("fee", "Fee invalid");
        }

        try {
            transferAmount = new BigDecimal(transferDTO.getTransactionAmount());
            if (transferAmount.compareTo(BigDecimalUtils.FIFTY) < 0
                    || transferAmount.compareTo(BigDecimalUtils.ONE_BILLION) > 0)
                errors.put("transaction", "Transfer amount must be between 50 and 1.000.000.000");

            BigDecimal totalAmount = BigDecimalUtils.percentage(transferAmount, fee.add(BigDecimalUtils.ONE_HUNDRED));

            if (totalAmount.compareTo(sender.getBalance()) > 0)
                errors.put("transaction", "Total amount cannot exceed the current balance");
        } catch (NumberFormatException nfe) {
            errors.put("transaction", "Transfer amount invalid");
        }

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            transfer = new Transfer(sender, recipient, fee, transferAmount);
            boolean success = customerService.transfer(transfer);
            if (!success)
                throw new IOException();

            Map<String, CustomerDTO> results = new HashMap<>();
            CustomerDTO senderDTO = customerService.selectCustomerDTOById(senderId);
            results.put("sender", senderDTO);
            CustomerDTO recipientDTO = customerService.selectCustomerDTOById(recipientId);
            results.put("recipient", recipientDTO);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (IOException ioe) {
            return new ResponseEntity<>("Database Error", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/recipients")
    public ResponseEntity<?> getRecipients(@PathVariable("id") String rawId) {
        Long id;

        try {
            id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new IllegalAccessException();
        } catch (NumberFormatException | IllegalAccessException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        List<CustomerDTO> recipients = customerService.selectRecipientsDTO(id);

        return new ResponseEntity<>(recipients, HttpStatus.OK);
    }

    @DeleteMapping("{id}/suspend")
    public ResponseEntity<?> suspend(@PathVariable("id") String rawId) {
        Long id;

        try {
            id = Long.parseLong(rawId);
            if (!customerService.ifExists(id))
                throw new IOException();
            if (customerService.ifSuspended(id))
                return new ResponseEntity<>("Customer suspended", HttpStatus.UNAUTHORIZED);
        } catch (NumberFormatException | IOException e) {
            return new ResponseEntity<>("Id invalid", HttpStatus.NOT_FOUND);
        }

        try {
            customerService.suspendCustomerById(id);
            CustomerDTO customerDTO = customerService.selectCustomerDTOById(id);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
