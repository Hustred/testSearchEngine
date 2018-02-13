package com.testcompany.web.service;

import com.testcompany.model.Document;
import com.testcompany.model.DocumentBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SearchService {
    protected final Map<String, Document> persistence;
    protected final DocumentBuilder documentBuilder;

    public SearchService(Map<String, Document> persistence, DocumentBuilder documentBuilder) {
        this.persistence = persistence;
        this.documentBuilder = documentBuilder;
    }

    public void putDocument(String key, String document) throws IOException {
        persistence.put(key, documentBuilder.generateDocument(document));
    }

    public String getDocument(String key) throws IOException {
        final Document document = persistence.get(key);
        return document == null ? null : document.getDocument();
    }

    public Set<String> search (Collection<String> tokens) throws IOException {
        return persistence.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getTokens().containsAll(tokens))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
