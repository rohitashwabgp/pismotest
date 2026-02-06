package com.pismo.test.cards.projections;

// real time database entity for different aggregations -> like last 5 days total, daily total, last 10 etc
public class AccountAggregate {
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    Double balance;
    Long account;
    // private final Long creditLimit = 1000L;


    public AccountAggregate(Long accountId) {
        this.account = accountId;
        this.balance = 0D;
    }

    public void update(Double amount) {
        this.balance = this.balance + amount;
    }


}
