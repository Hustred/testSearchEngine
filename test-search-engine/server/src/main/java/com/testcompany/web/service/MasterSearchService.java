package com.testcompany.web.service;

import com.testcompany.SlaveClient;
import com.testcompany.model.Document;
import com.testcompany.model.DocumentBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MasterSearchService extends SearchService {
    private final SlaveClient slave1Client;
    private final SlaveClient slave2Client;

    public MasterSearchService(Map<String, Document> persistence, DocumentBuilder documentBuilder, SlaveClient slave1Client, SlaveClient slave2Client) {
        super(persistence, documentBuilder);
        this.slave1Client = slave1Client;
        this.slave2Client = slave2Client;
    }

    @Override
    public void putDocument(String key, String document) throws IOException {
        Random random = new Random();
        int randomNum = random.nextInt(3);
        switch (randomNum) {
            case 0:
                super.putDocument(key, document);
                break;
            case 1:
                slave1Client.putDocument(key, document);
                break;
            case 2:
                slave2Client.putDocument(key, document);
                break;
        }
    }

    @Override
    public String getDocument(String key) throws IOException {
        final String document = super.getDocument(key);
        if (document != null) {
            return document;
        }
        final String slave1Document = slave1Client.getDocument(key);
        if (slave1Document != null) {
            return slave1Document;
        }
        final String slave2Document = slave2Client.getDocument(key);
        if (slave2Document != null) {
            return slave2Document;
        }
        return null;
    }

    @Override
    public Set<String> search (Collection<String> tokens) throws IOException {
        final Set<String> search = super.search(tokens);
        search.addAll(slave1Client.search(tokens));
        search.addAll(slave2Client.search(tokens));
        return search;
    }
}
