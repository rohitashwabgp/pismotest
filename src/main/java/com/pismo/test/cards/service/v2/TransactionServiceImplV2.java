package com.pismo.test.cards.service.v2;

import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dao.TransactionDao;
import com.pismo.test.cards.domn.Transactions;
import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.dto.response.Transaction;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImplV2 implements TransactionServiceV2 {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    public TransactionServiceImplV2(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    @Transactional(rollbackFor = AppBusinessException.class)
    public Transaction process(TransactionRequest transactionReq) throws AppBusinessException {
        validateTransactionRequest(transactionReq);
        try {
            Transactions transaction = new Transactions.Builder().eventDate(LocalDateTime.now()).accountId(transactionReq.getAccountId()).amount(transactionReq.getAmount()).operationTypeId(transactionReq.getOperationTypeId()).build();
            this.transactionDao.save(transaction);
            return new Transaction.Builder().accountId(transaction.getAccountId()).amount(transaction.getAmount()).eventDate(transaction.getEventDate()).operationTypeId(transaction.getOperationTypeId()).accountId(transaction.getAccountId()).build();
        } catch (Exception ex) {
            throw new AppBusinessException(ex.getMessage(), String.valueOf(transactionReq.getAccountId()), 500);
        }
    }

    private void validateTransactionRequest(TransactionRequest transactionReq) throws AppBusinessException {
        if (!accountDao.existsById(transactionReq.getAccountId())) {
            throw new AppBusinessException("Account does not exist", String.valueOf(transactionReq.getAccountId()), 404);
        }

        if (transactionReq.getOperationTypeId() != null && OperationType.fromId(transactionReq.getOperationTypeId()).isEmpty()) {
            throw new AppBusinessException("Operation not valid", String.valueOf(transactionReq.getAccountId()), 404);
        }
    }

}
