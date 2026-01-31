package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.mock.TransactionQueue;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionQueue queue;
    private final TransactionConsumer consumer;
    private final AccountDao accountDao;

    public TransactionServiceImpl(TransactionQueue queue, TransactionConsumer consumer, AccountDao accountDao) {
        this.queue = queue;
        this.consumer = consumer;
        this.accountDao = accountDao;
    }

    @Override
    public void process(TransactionRequest transaction) throws AppBusinessException {
        if (accountDao.existsById(transaction.getAccountId())) {
            this.queue.register(consumer);
            this.queue.start();
            this.queue.sendMessage(transaction);
        } else {
            throw new AppBusinessException("Account does not exist", String.valueOf(transaction.getAccountId()), 404);
        }
    }

}
