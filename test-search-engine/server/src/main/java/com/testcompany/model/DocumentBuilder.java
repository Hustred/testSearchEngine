package com.testcompany.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DocumentBuilder {
    public Document generateDocument(String document) {
        return new Document(document, this.extractTokens(document));
    }


    private Set<String> extractTokens(String document) {
        String tokensInDocument = removeNotAlphaNumericCharactersAndExtraWhiteSpaces(document);
        return Arrays.asList(tokensInDocument.split(" "))
                .stream()
                .distinct()
                .collect(Collectors.toSet());
    }

    private String removeNotAlphaNumericCharactersAndExtraWhiteSpaces(String document) {
        return document.replaceAll("[^\\w\\s]|_", "").replaceAll("\\s{2,}", " ").trim();
    }
}
