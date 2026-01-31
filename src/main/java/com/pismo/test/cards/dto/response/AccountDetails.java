package com.pismo.test.cards.dto.response;

/**
 * Lombok is added in dependency - can be used .
 * Created own builder for ease of demonstration & compilation without plugin
 */
public class AccountDetails {
    long accountId;
    String documentId;

    private AccountDetails(Builder builder) {
        this.accountId = builder.accountId;
        this.documentId = builder.documentId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public static class Builder {
        long accountId;
        String documentId;

        public Builder accountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public AccountDetails build() {
            return new AccountDetails(this);
        }
    }
}
