package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.dto.response.Transaction;
import com.pismo.test.cards.exception.AppBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransactionDecoratorV2 implements TransactionServiceV2 {
    private static final Logger log = LoggerFactory.getLogger(TransactionDecoratorV2.class);

    protected final TransactionServiceV2 transactionServiceV2;

    protected TransactionDecoratorV2(TransactionServiceV2 transactionServiceV2) {
        this.transactionServiceV2 = transactionServiceV2;
    }

    @Override
    public Transaction process(TransactionRequest transaction) throws AppBusinessException {
        log.info("TransactionDecorator starts {}", transaction.getAccountId());
        return transactionServiceV2.process(transaction);
    }
}
