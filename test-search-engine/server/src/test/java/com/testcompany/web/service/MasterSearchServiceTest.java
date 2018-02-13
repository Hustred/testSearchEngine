package com.testcompany.web.service;

import com.testcompany.SlaveClient;
import com.testcompany.model.Document;
import com.testcompany.model.DocumentBuilder;
import org.junit.Test;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MasterSearchServiceTest {

    private Map<String, Document> persistence = mock(Map.class);
    private DocumentBuilder documentBuilder = new DocumentBuilder();
    private SlaveClient slave1Client = mock(SlaveClient.class);
    private SlaveClient slave2Client = mock(SlaveClient.class);

    private final MasterSearchService masterSearchService = new MasterSearchService(persistence, documentBuilder, slave1Client, slave2Client);

    @Test
    public void testPutDocument() throws Exception {
        for(int i=0; i<10000; i++) {
            masterSearchService.putDocument("" + i, "");
        }
        verify(persistence, atLeastOnce()).put(any(), any());
        verify(slave1Client, atLeastOnce()).putDocument(any(), any());
        verify(slave2Client, atLeastOnce()).putDocument(any(), any());
    }

    @Test
    public void testGetDocument() throws Exception {
        when(persistence.get(any())).thenReturn(null);
        when(slave1Client.getDocument(any())).thenReturn(null);
        masterSearchService.getDocument("someKey");
        verify(persistence).get(any());
        verify(slave1Client).getDocument(any());
        verify(slave2Client).getDocument(any());
    }

    @Test
    public void testSearch() throws Exception {
        final HashSet<Map.Entry<String, Document>> value = new HashSet<>();
        HashMap<String, Document> map = new HashMap<>();
        map.put("key1", documentBuilder.generateDocument("token1 token2"));
        map.put("key2", documentBuilder.generateDocument("token2 token3"));
        when(persistence.entrySet()).thenReturn(map.entrySet());
        when(slave1Client.search(any())).thenReturn(Collections.singletonList("key4"));
        when(slave2Client.search(any())).thenReturn(Arrays.asList("key8", "key11"));
        final Collection<String> search = masterSearchService.search(Arrays.asList("token1", "token2"));
        assertThat(search).hasSize(4);
        assertThat(search).containsAll(Arrays.asList("key1", "key4", "key8", "key11"));
    }
}