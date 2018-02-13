package com.testcompany;

import com.google.gson.Gson;
import com.testcompany.web.dto.response.DocumentResponse;
import com.testcompany.web.dto.response.SearchResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SlaveClient {

    private final int port;
    private static final String host = "localhost";
    private static final String documentsPath = "/searchApi/documents";
    private static final String searchPath = "/searchApi/search";
    private final HttpClient httpClient;

    public SlaveClient(int port, HttpClient httpClient) {
        this.port = port;
        this.httpClient = httpClient;
    }

    public void putDocument(String key, String document) throws IOException {
        final String uri = "http://" + host + ':' + port + documentsPath;
        HttpPut httpPut = new HttpPut(uri);
        httpPut.addHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(buildDocumentPutRequest(key, document), Charset.defaultCharset());
        httpPut.setEntity(stringEntity);
        try {
            httpClient.execute(httpPut);
        } catch (IOException e) {
            System.out.println("Putting document failed " + e.getMessage());
            throw e;
        }
    }

    public String getDocument(String key) throws IOException {
        final String uri = "http://" + host + ':' + port + documentsPath + '/' + key;
        HttpGet httpGet = new HttpGet(uri);
        try {
            final HttpResponse httpResponse = httpClient.execute(httpGet);
            Reader reader = new InputStreamReader(httpResponse.getEntity().getContent());
            Gson gson = new Gson();
            final DocumentResponse documentResponse = gson.fromJson(reader, DocumentResponse.class);
            return documentResponse.getDocument();
        } catch (IOException e) {
            System.out.println("Getting document failed " + e.getMessage());
            throw e;
        }
    }

    public Collection<String> search(Collection<String> tokens) throws IOException {
        final String uri = "http://" + host + ':' + port + searchPath;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(buildSearchRequest(tokens), Charset.defaultCharset());
        httpPost.setEntity(stringEntity);
        try {
            final HttpResponse httpResponse = httpClient.execute(httpPost);
            Reader reader = new InputStreamReader(httpResponse.getEntity().getContent());
            Gson gson = new Gson();
            final SearchResponse searchResponse = gson.fromJson(reader, SearchResponse.class);
            return searchResponse.getKeys();
        } catch (IOException e) {
            System.out.println("Search failed " + e.getMessage());
            throw e;
        }
    }

    private static String buildDocumentPutRequest(String key, String document) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("key", key);
        fields.put("document", document);

        return new Gson().toJson(fields);
    }

    private static String buildSearchRequest(Collection<String> tokens) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("tokens", tokens);

        return new Gson().toJson(fields);
    }
}
