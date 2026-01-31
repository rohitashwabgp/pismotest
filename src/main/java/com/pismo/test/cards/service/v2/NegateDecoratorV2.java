package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.dto.response.Transaction;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.stereotype.Service;

@Service
public class NegateDecoratorV2 extends TransactionDecoratorV2 {
    protected NegateDecoratorV2(TransactionServiceV2 transactionServiceV2) {
        super(transactionServiceV2);
    }

    @Override
    public Transaction process(TransactionRequest transaction) throws AppBusinessException {
        transaction.setAmount(-transaction.getAmount());
        return transactionServiceV2.process(transaction);
    }
}
