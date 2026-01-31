package com.pismo.test.cards.cache;

import com.pismo.test.cards.dao.OperationTypeDao;
import com.pismo.test.cards.domn.OperationType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppCache {

    private final Map<Integer, OperationType> cache = new ConcurrentHashMap<>();
    private final OperationTypeDao operationTypeDao;

    public AppCache(OperationTypeDao operationTypeDao) {
        this.operationTypeDao = operationTypeDao;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = true)
    public void load() {
        operationTypeDao.findAll()
                .forEach(a -> cache.put(a.getId(), a));
    }

    public OperationType get(int id) {
        return cache.get(id);
    }
}
