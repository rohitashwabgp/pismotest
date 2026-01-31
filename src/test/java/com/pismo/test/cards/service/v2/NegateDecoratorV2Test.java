package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NegateDecoratorV2Test {
    @InjectMocks
    private NegateDecoratorV2 decorator;

    @Mock
    private TransactionServiceV2 service;

    @Captor
    ArgumentCaptor<TransactionRequest> amountCaptor;

    @Test
    void verify_transaction_service_interaction_and_negative_val_when_debit_process() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        decorator.process(request);
        verify(service, times(1)).process(amountCaptor.capture());
        assertEquals(-amount, amountCaptor.getValue().getAmount());
    }
}