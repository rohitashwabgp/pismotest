package com.pismo.test.cards.domn;

import jakarta.persistence.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column(nullable = false, unique = true)
    String documentNumber;

    public Account() {
    }


    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
