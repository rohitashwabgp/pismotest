package com.pismo.test.cards.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AccountDto {
    @NotBlank
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    String documentId;
}
