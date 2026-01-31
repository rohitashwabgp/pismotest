package com.pismo.test.cards.consumers;

public interface Consumer<T> {
    void consume(T item);
}
