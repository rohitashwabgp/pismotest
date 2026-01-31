package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dao.TransactionDao;
import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplV2Test {
    @Mock
    private AccountDao accountDao;
    @Mock
    private TransactionDao transactionDao;
    @InjectMocks
    TransactionServiceImplV2 transactionServiceImplV2;

    @Test
    void exception_account_does_not_exists_when_process() {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        Exception ex = assertThrows(AppBusinessException.class, () -> transactionServiceImplV2.process(request));
        assertEquals("Account does not exist", ex.getMessage());
    }

    @Test
    void exception_operation_not_valid_when_process() {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        request.setAccountId(1234L);
        request.setOperationTypeId(6);
        when(accountDao.existsById(request.getAccountId())).thenReturn(true);
        Exception ex = assertThrows(AppBusinessException.class, () -> transactionServiceImplV2.process(request));
        assertEquals("Operation not valid", ex.getMessage());
    }

    @Test
    void exception_account_does_not_exists_when_process_1() throws AppBusinessException {
        TransactionRequest request = new TransactionRequest();
        double amount = 200;
        request.setAmount(amount);
        request.setAccountId(1234L);
        request.setOperationTypeId(3);
        when(accountDao.existsById(request.getAccountId())).thenReturn(true);
        transactionServiceImplV2.process(request);
    }
}