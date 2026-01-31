package com.pismo.test.cards.service.v1.transaction;

import com.pismo.test.cards.consumers.Consumer;
import com.pismo.test.cards.dao.TransactionDao;
import com.pismo.test.cards.domn.Transactions;
import com.pismo.test.cards.dto.request.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionConsumer implements Consumer<TransactionRequest> {
    private final TransactionDao transactionDao;
    private static final Logger log = LoggerFactory.getLogger(TransactionDecorator.class);

    public TransactionConsumer(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    @Transactional
    public void consume(TransactionRequest item) {
        log.info("Message received - processing");
        Transactions transaction = new Transactions.Builder()
                .eventDate(LocalDateTime.now())
                .accountId(item.getAccountId())
                .amount(item.getAmount())
                .operationTypeId(item.getOperationTypeId())
                .build();
        this.transactionDao.save(transaction);
        log.info("Message processed - {}", transaction.getTransactionId());
    }
}
