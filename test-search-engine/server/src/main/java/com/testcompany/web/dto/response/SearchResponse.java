package com.testcompany.web.dto.response;

import java.util.Collection;

public class SearchResponse {
    private Collection<String> keys;

    public SearchResponse() {
    }

    public SearchResponse(Collection<String> keys) {
        this.keys = keys;
    }

    public Collection<String> getKeys() {
        return keys;
    }

    public void setKeys(Collection<String> keys) {
        this.keys = keys;
    }
}
