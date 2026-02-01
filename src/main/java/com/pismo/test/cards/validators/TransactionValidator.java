package com.pismo.test.cards.validators;

import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.pismo.test.cards.enums.OperationType.fromId;

@Component
public class TransactionValidator {

    private final AccountDao accountDao;

    public TransactionValidator(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public static Optional<OperationType> getOperationType(TransactionRequest transaction) throws AppBusinessException {
        Optional<OperationType> operationType = fromId(transaction.getOperationTypeId());
        if (operationType.isEmpty()) {
            throw new AppBusinessException("Invalid operation type", String.valueOf(transaction.getAccountId()), 404);
        }
        return operationType;
    }

    public void validateTransactionRequest(TransactionRequest transactionReq) throws AppBusinessException {
        if (!accountDao.existsById(transactionReq.getAccountId())) {
            throw new AppBusinessException("Account does not exist", String.valueOf(transactionReq.getAccountId()), 404);
        }

        if (transactionReq.getOperationTypeId() != null && OperationType.fromId(transactionReq.getOperationTypeId()).isEmpty()) {
            throw new AppBusinessException("Operation not valid", String.valueOf(transactionReq.getAccountId()), 404);
        }
    }

}
