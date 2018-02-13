package com.testcompany.web.dto.request;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class SearchDTO {
    @NotNull
    private Set<String> tokens;

    public Set<String> getTokens() {
        return tokens;
    }

    public void setTokens(Set<String> tokens) {
        this.tokens = tokens;
    }
}
