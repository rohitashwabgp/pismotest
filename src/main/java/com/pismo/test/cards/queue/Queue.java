package com.pismo.test.cards.queue;

import com.pismo.test.cards.consumers.Consumer;
import com.pismo.test.cards.dto.request.TransactionRequest;

import java.util.concurrent.BlockingQueue;

public interface Queue<T> {

    BlockingQueue<TransactionRequest> getQueue();

    void sendMessage(T t) throws InterruptedException;

    void register(Consumer<TransactionRequest> consumerRequest);

    boolean isRegistered(Consumer<TransactionRequest> consumer);

    boolean isRunning();

    void start();

}
