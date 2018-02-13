package com.testcompany.web;

import com.testcompany.web.dto.request.DocumentPutDTO;
import com.testcompany.web.dto.request.SearchDTO;
import com.testcompany.web.dto.response.DocumentResponse;
import com.testcompany.web.dto.response.SearchResponse;
import com.testcompany.web.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @ResponseStatus (HttpStatus.ACCEPTED)
    @RequestMapping(value = "/searchApi/documents", method = RequestMethod.PUT)
    public void putIndex(@Valid @RequestBody final DocumentPutDTO documentPutDTO) throws IOException {
        searchService.putDocument(documentPutDTO.getKey(), documentPutDTO.getDocument());
    }

    @RequestMapping(value = "/searchApi/search", method = RequestMethod.POST)
    public SearchResponse search(@Valid @RequestBody final SearchDTO searchDTO) throws IOException {
        return new SearchResponse(searchService.search(searchDTO.getTokens()));
    }

    @RequestMapping(value = "/searchApi/documents/{documentKey}", method = RequestMethod.GET)
    public DocumentResponse getDocument(@PathVariable("documentKey") String documentKey) throws IOException {
        return new DocumentResponse(searchService.getDocument(documentKey));
    }
}
