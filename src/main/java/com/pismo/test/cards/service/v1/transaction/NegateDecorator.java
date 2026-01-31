package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.stereotype.Service;

@Service
public class NegateDecorator extends TransactionDecorator {
    protected NegateDecorator(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    public void process(TransactionRequest transaction) throws AppBusinessException {
        transaction.setAmount(-transaction.getAmount());
        transactionService.process(transaction);
    }
}
