package com.pismo.test.cards.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * OperationType - Mocked
 * Originally in cache - Appcache in cache package
 *
 */
public enum OperationType {

    NORMAL_PURCHASE(1, "Normal Purchase"), PURCHASE_WITH_INSTALLMENT(2, "Purchase with installments"), WITHDRAWAL(3, "Withdrawal"), CREDIT_VOUCHER(4, "Credit Voucher");


    private final int id;
    private final String description;

    OperationType(int id, String description) {
        this.id = id;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<OperationType> fromId(int id) {
        return Arrays.stream(values()).filter(data -> data.id == id).findFirst();
    }

}
