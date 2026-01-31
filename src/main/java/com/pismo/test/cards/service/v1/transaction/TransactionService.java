package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;

public interface TransactionService {
    void process(TransactionRequest transaction) throws AppBusinessException;
}
