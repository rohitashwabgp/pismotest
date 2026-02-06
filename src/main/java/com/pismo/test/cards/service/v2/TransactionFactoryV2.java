package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.enums.OperationType;
import org.springframework.stereotype.Service;

@Service
public class TransactionFactoryV2 {
    private final TransactionServiceV2 transactionServiceImplV2;
    private final NegateDecoratorV2 negateDecoratorV2;

    public TransactionFactoryV2(TransactionServiceImplV2 transactionServiceImplV2, NegateDecoratorV2 negateDecoratorV2) {
        this.transactionServiceImplV2 = transactionServiceImplV2;
        this.negateDecoratorV2 = negateDecoratorV2;
    }

    public TransactionServiceV2 get(OperationType type) {
        return switch (type) {
            case NORMAL_PURCHASE, PURCHASE_WITH_INSTALLMENT, WITHDRAWAL -> negateDecoratorV2;
            case CREDIT_VOUCHER, PAYMENT -> transactionServiceImplV2;
        };
    }
}
