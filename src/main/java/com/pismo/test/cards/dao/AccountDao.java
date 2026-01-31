package com.pismo.test.cards.dao;

import com.pismo.test.cards.domn.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Long> {

}
