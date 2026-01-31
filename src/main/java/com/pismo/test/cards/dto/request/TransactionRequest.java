package com.pismo.test.cards.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TransactionRequest {
    @NotBlank
    Long accountId;
    @NotBlank
    Integer operationTypeId;
    @NotBlank
    Double amount;

    public TransactionRequest() {
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
