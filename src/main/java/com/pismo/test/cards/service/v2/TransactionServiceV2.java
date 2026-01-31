package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.dto.response.Transaction;
import com.pismo.test.cards.exception.AppBusinessException;

public interface TransactionServiceV2 {
    Transaction process(TransactionRequest transaction) throws AppBusinessException;
}
