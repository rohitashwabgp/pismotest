package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.enums.OperationType;
import org.springframework.stereotype.Service;

@Service
public class TransactionFactory {
    private final TransactionService transactionService;
    private final NegateDecorator negateDecorator;

    public TransactionFactory(TransactionServiceImpl transactionServiceImpl, NegateDecorator negateDecorator) {
        this.transactionService = transactionServiceImpl;
        this.negateDecorator = negateDecorator;
    }

    public TransactionService get(OperationType type) {
        return switch (type) {
            case NORMAL_PURCHASE, PURCHASE_WITH_INSTALLMENT, WITHDRAWAL -> negateDecorator;
            case CREDIT_VOUCHER, PAYMENT -> transactionService;
        };
    }
}
