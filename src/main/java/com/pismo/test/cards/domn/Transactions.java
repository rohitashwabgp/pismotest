package com.pismo.test.cards.domn;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")
public class Transactions {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;


    @Column(nullable = false)
    Long accountId;
    @Column(nullable = false)
    Integer operationTypeId;
    @Column(nullable = false)
    Double amount;
    @Column(nullable = false)
    LocalDateTime eventDate;

    public Transactions() {

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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    private Transactions(Builder builder) {
        this.accountId = builder.accountId;
        this.amount = builder.amount;
        this.operationTypeId = builder.operationTypeId;
        this.eventDate = builder.eventDate;
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

        public Transactions build() {
            return new Transactions(this);
        }

    }
}
