package com.pismo.test.cards.dao;

import com.pismo.test.cards.domn.Account;
import com.pismo.test.cards.domn.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transactions, Long> {

}
