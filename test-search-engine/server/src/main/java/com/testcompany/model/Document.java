package com.testcompany.model;

import java.util.Set;

public class Document {
    private final String document;
    private final Set<String> tokens;

    public Document(String document, Set<String> tokens) {
        this.document = document;
        this.tokens = tokens;
    }

    public String getDocument() {
        return document;
    }

    public Set<String> getTokens() {
        return tokens;
    }
}
