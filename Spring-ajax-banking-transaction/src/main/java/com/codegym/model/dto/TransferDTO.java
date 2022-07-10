package com.codegym.model.dto;

import com.codegym.model.Customer;
import com.codegym.model.Transfer;
import com.codegym.utility.ValidationUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class TransferDTO {
    @Length(min = 1, message = "transactionAmount invalid 2")
    @Pattern(regexp = ValidationUtils.NUMBERONLY_REGEX, message = "transactionAmount invalid")
    private String transactionAmount;
    private String fee;
    private String senderId;

    @NotEmpty(message = "recipientid empty")
    @Length(min = 1, message = "Id invalid 2")
    @Pattern(regexp = ValidationUtils.NUMBERONLY_REGEX, message = "Id invalid")
    private String recipientId;

    public TransferDTO() {
    }

    public TransferDTO(String transactionAmount, String fee, String senderId, String recipientId) {
        this.transactionAmount = transactionAmount;
        this.fee = fee;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public Transfer toTransfer(TransferDTO transferDTO, Customer sender, Customer recipient) {
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setFee(new BigDecimal(transferDTO.getFee()));
        transfer.setTransactionAmount(new BigDecimal(transferDTO.getTransactionAmount()));
        return transfer;
    }
}
