package com.pismo.test.cards.dao;

import com.pismo.test.cards.domn.Account;
import com.pismo.test.cards.domn.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeDao extends JpaRepository<OperationType, Long> {

}
