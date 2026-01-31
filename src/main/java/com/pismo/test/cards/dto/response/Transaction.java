package com.pismo.test.cards.dto.response;


import java.time.LocalDateTime;

public class Transaction {
    long accountId;
    int operationTypeId;
    double amount;
    LocalDateTime eventDate;
    long transactionId;

    public Transaction(Builder builder) {
        this.accountId = builder.accountId;
        this.amount = builder.amount;
        this.operationTypeId = builder.operationTypeId;
        this.eventDate = builder.eventDate;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public long getTransactionId() {
        return transactionId;
    }


    public long getAccountId() {
        return accountId;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public static class Builder {
        long accountId;
        int operationTypeId;
        double amount;
        LocalDateTime eventDate;

        public Builder accountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder operationTypeId(int operationTypeId) {
            this.operationTypeId = operationTypeId;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder eventDate(LocalDateTime eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }

    }

}
