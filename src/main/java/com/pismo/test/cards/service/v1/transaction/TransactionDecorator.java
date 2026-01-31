package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransactionDecorator implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionDecorator.class);

    protected final TransactionService transactionService;

    protected TransactionDecorator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void process(TransactionRequest transaction) throws AppBusinessException {
        log.info("TransactionDecorator starts {}", transaction.getAccountId());
        transactionService.process(transaction);
        log.info("TransactionDecorator ends {}", transaction.getAccountId());
    }
}
