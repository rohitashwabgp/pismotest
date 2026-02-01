package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.queue.TransactionQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    AccountDao accountDao;
    @Mock
    private  TransactionQueue queue;
    @Mock
    private  TransactionConsumer consumer;

    @Test
    void process_throws_exception_when_accountId_null() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(200D);
        assertThrows(AppBusinessException.class, () -> transactionService.process(request));
    }

    @Test
    void process_calls_consumer_when_success() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(200D);
        when(accountDao.existsById(any())).thenReturn(true);
        transactionService.process(request);
        verify(queue,times(1)).register(consumer);
        verify(queue, times(1)).sendMessage(request);
    }
}