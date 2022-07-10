package com.codegym.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id = 0L;

    @NotNull(message = "Transaction amount cannot be left empty")
    @Range(min = 50, max = 1000000000, message = "Transaction amount must be between 50 and 1.000.000.000")
    @Column(precision = 12)
    private BigDecimal transactionAmount;

    @Range(min = 0, max = 100)
    @Column(precision = 3)
    private BigDecimal fee = new BigDecimal(10);

    @Column(precision = 12)
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipientId", referencedColumnName = "id")
    private Customer recipient;

    public Transfer() {
    }

    public Transfer(Long id, BigDecimal transactionAmount, BigDecimal fee, BigDecimal feeAmount, Customer sender, Customer recipient) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.fee = fee;
        this.feeAmount = feeAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Transfer(Customer sender, Customer recipient, BigDecimal fee, BigDecimal transactionAmount) {
        this.sender = sender;
        this.recipient = recipient;
        this.fee = fee;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}
