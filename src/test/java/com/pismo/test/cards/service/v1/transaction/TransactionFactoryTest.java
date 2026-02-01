package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionFactoryTest {

    @InjectMocks
    private TransactionFactory factory;

    @Mock
    private TransactionServiceImpl transactionService;

    private NegateDecorator negateDecoratorSpy;

    @Mock
    private NegateDecorator negateDecorator;

    @Captor
    ArgumentCaptor<TransactionRequest> amountCaptor;

    @BeforeEach
    void setup() {
        negateDecoratorSpy = new NegateDecorator(transactionService);
    }

    @Test
    void get_service_PURCHASE_WITH_INSTALLMENT_then_debitDecorator() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        factory.get(OperationType.PURCHASE_WITH_INSTALLMENT).process(request);
        verify(negateDecorator, times(1)).process(amountCaptor.capture());
        assertEquals(amount, amountCaptor.getValue().getAmount());

    }

    @Test
    void get_service_CREDIT_VOUCHERT_then_no_negate_original_transaction_service() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        factory.get(OperationType.CREDIT_VOUCHER).process(request);
        verify(transactionService, times(1)).process(amountCaptor.capture());
        assertEquals(amount, amountCaptor.getValue().getAmount());

    }

    @Test
    void get_when_negateDecorator_then_negate_amount() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        NegateDecorator spyDecorator = Mockito.spy(negateDecoratorSpy);
        spyDecorator.process(request);
        verify(transactionService, times(1)).process(amountCaptor.capture());
        assertEquals(-amount, amountCaptor.getAllValues().get(0).getAmount());
    }
}