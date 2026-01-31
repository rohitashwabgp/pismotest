package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionFactoryV2Test {

    @InjectMocks
    private TransactionFactoryV2 factory;

    @Mock
    private TransactionServiceImplV2 transactionServiceImplV2;

    private NegateDecoratorV2 debitDecoratorSpy;
    @Mock
    private NegateDecoratorV2 debitDecorator;

    @Captor
    ArgumentCaptor<TransactionRequest> amountCaptor;

    @BeforeEach
    void setup() {
        debitDecoratorSpy = new NegateDecoratorV2(transactionServiceImplV2);
    }

    @Test
    void get_service_PURCHASE_WITH_INSTALLMENT_then_debitDecorator() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        factory.get(OperationType.PURCHASE_WITH_INSTALLMENT).process(request);
        verify(debitDecorator, times(1)).process(amountCaptor.capture());
        assertEquals(amount, amountCaptor.getValue().getAmount());
    }

    @Test
    void get_service_CREDIT_VOUCHERT_then_original_transaction_service() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        TransactionServiceV2 transactionServiceV2 = factory.get(OperationType.CREDIT_VOUCHER);
        transactionServiceV2.process(request);
        verify(transactionServiceImplV2, times(1)).process(amountCaptor.capture());
        assertEquals(amount, amountCaptor.getValue().getAmount());

    }

    @Test
    void when_debit_then_negate_amount_get() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        NegateDecoratorV2 spyDecorator = Mockito.spy(debitDecoratorSpy);
        spyDecorator.process(request);
        verify(transactionServiceImplV2, times(1)).process(amountCaptor.capture());
        assertEquals(-amount, amountCaptor.getAllValues().get(0).getAmount());
    }
}