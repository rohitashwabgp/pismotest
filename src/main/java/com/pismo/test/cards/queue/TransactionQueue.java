package com.pismo.test.cards.queue;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.consumers.Consumer;
import com.pismo.test.cards.dto.request.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TransactionQueue implements Queue<TransactionRequest> {

    private static final Logger log = LoggerFactory.getLogger(TransactionQueue.class);

    BlockingQueue<TransactionRequest> queue;
    private volatile boolean running = false;
    Thread consumerThread;
    private final Set<Consumer<TransactionRequest>> consumerList = new CopyOnWriteArraySet<>();

    public TransactionQueue(PropertySource propertySource) {

        queue = new LinkedBlockingQueue<>(propertySource.getTransaction().getQueueSize());

    }

    @Override
    public void register(Consumer<TransactionRequest> consumer) {
        if (!isRegistered(consumer))
            this.consumerList.add(consumer);
    }

    @Override
    public boolean isRegistered(Consumer<TransactionRequest> consumer) {
        return consumerList.contains(consumer);
    }

    @Override
    public boolean isRunning() {
        return consumerThread != null && this.running && !this.consumerList.isEmpty();
    }

    @Override
    public void start() {
        if (!isRunning())
            startConsumer();
    }

    private void startConsumer() {
        consumerThread = new Thread(() -> {
            while (running) {
                try {
                    TransactionRequest item = queue.take();
                    log.info("Item received account - {}", item.getAccountId());
                    for (Consumer<TransactionRequest> consumer : consumerList) {
                        consumer.consume(item);
                    }
                    log.info("Item sent to queue - {}", item.getAccountId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        consumerThread.start();
        this.running = true;
    }

    @Override
    public BlockingQueue<TransactionRequest> getQueue() {
        return queue;
    }

    @Override
    public void sendMessage(TransactionRequest transaction) {
        try {
            log.info("sending message account id {}", transaction.getAccountId());
            queue.put(transaction);
            log.info("message sent account id {}", transaction.getAccountId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        consumerThread.interrupt();
    }

}
