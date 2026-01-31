package com.pismo.test.cards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.cards")
public class PropertySource {

    private Account account = new Account();
    private Transaction transaction = new Transaction();
    private Jwk jwk = new Jwk();

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Jwk getJwk() {
        return jwk;
    }


    public void setJwk(Jwk jwk) {
        this.jwk = jwk;
    }

    public static class Account {
        private Upload upload = new Upload();

        public Upload getUpload() {
            return upload;
        }

        public void setUpload(Upload upload) {
            this.upload = upload;
        }

        public static class Upload {
            private String path;
            private long maxSize;
            private List<String> allowedTypes;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public long getMaxSize() {
                return maxSize;
            }

            public void setMaxSize(long maxSize) {
                this.maxSize = maxSize;
            }

            public List<String> getAllowedTypes() {
                return allowedTypes;
            }

            public void setAllowedTypes(List<String> allowedTypes) {
                this.allowedTypes = allowedTypes;
            }
        }
    }

    public static class Transaction {
        public void setQueueSize(int queueSize) {
            this.queueSize = queueSize;
        }

        public int getQueueSize() {
            return queueSize;
        }

        private int queueSize;
    }

    public static class Jwk {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}