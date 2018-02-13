package com.testcompany;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class ClientLauncher {
    private final int port = 8080;
    private static final String host = "localhost";
    private static final String documentsPath = "/searchApi/documents";
    private static final String searchPath = "/searchApi/search";
    private final HttpClient httpClient = HttpClientBuilder.create().build();

    public static void main(String... args) {
        ClientLauncher clientLauncher = new ClientLauncher();
        for (int i=0; i<100; i++){
            clientLauncher.putDocument(String.valueOf(i), "mod5is"+i%5+ ' ' +"mod3is"+i%3+ ' ' +i);
        }
        clientLauncher.getDocument("42");
        clientLauncher.getDocument("78");
        clientLauncher.getDocument("29");
        clientLauncher.search(Arrays.asList("mod5is0", "mod3is0"));
        clientLauncher.search(Collections.singletonList("42"));
        clientLauncher.search(Collections.singletonList("mod5is0"));
        clientLauncher.search(Arrays.asList("mod5is0", "mod3is0", "30"));
        clientLauncher.search(Arrays.asList("mod5is2", "mod3is1"));
    }

    public void putDocument(String key, String document) {
        final String uri = "http://" + host + ':' + port + documentsPath;
        HttpPut httpPut = new HttpPut(uri);
        httpPut.addHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(buildDocumentPutRequest(key, document), Charset.defaultCharset());
        httpPut.setEntity(stringEntity);
        try {
            httpClient.execute(httpPut);
        } catch (IOException e) {
            System.out.println("Putting document failed " + e.getMessage());
        }
    }

    public void getDocument(String key){
        final String uri = "http://" + host + ':' + port + documentsPath + '/' + key;
        HttpGet httpGet = new HttpGet(uri);
        try {

            final HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), Charset.defaultCharset()));
        } catch (IOException e) {
            System.out.println("Getting document failed " + e.getMessage());
        }
    }

    public void search(Collection<String> tokens){
        final String uri = "http://" + host + ':' + port + searchPath;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(buildSearchRequest(tokens), Charset.defaultCharset());
        httpPost.setEntity(stringEntity);
        try {
            final HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), Charset.defaultCharset()));
        } catch (IOException e) {
            System.out.println("Search failed " + e.getMessage());
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
