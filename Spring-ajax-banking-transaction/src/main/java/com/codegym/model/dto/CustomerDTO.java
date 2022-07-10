package com.codegym.model.dto;

import com.codegym.model.Customer;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerDTO implements Serializable {
    private Long id;

    @NotBlank(message = "Customer name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-z]*([ ][A-Z][a-z]*)*$", message = "Name has to begin with capital letters")
    @Length(min = 1, max = 50, message = "Customer name has to be between 5 and 50 characters")
    private String fullName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email invalid")
    @Length(min = 5, max = 50, message = "Email has to be between 5 and 50 characters")
    private String email;

    @Pattern(regexp = "^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", message = "Please use a valid number following this pattern: +91 (123) 456 7890")
    @Length(min = 5, max = 20, message = "Phone number has to be between 5 and 20 characters")
    private String phone;

    @Length(min = 5, max = 200, message = "Address has to be between 5 and 200 characters")
    private String address;

    private BigDecimal balance;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public CustomerDTO(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer toCustomer() {
        Customer customer = new Customer();

        customer.setId(this.id);
        customer.setFullName(this.fullName);
        customer.setEmail(this.email);
        customer.setPhone(this.phone);
        customer.setAddress(this.address);
        customer.setBalance(this.balance);

        return customer;
    }
}
