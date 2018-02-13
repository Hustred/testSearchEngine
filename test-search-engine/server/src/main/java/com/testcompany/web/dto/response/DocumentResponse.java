package com.testcompany.web.dto.response;

public class DocumentResponse {
    private String document;

    public DocumentResponse() {
    }

    public DocumentResponse(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
