package com.testcompany.web.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class DocumentPutDTO {
    @NotEmpty(message = "Key may not be empty")
    private String key;
    @NotEmpty(message = "Document may not be empty")
    private String document;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(final String document) {
        this.document = document;
    }
}
