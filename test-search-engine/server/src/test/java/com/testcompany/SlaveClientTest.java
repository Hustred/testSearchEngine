package com.testcompany;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SlaveClientTest {

    private HttpClient client =  mock(HttpClient.class);
    private int port = 12345;
    private SlaveClient slaveClient = new SlaveClient(port, client);

    @Test
    public void testPutDocument() throws Exception {
        slaveClient.putDocument("key", "document");
        verify(client).execute(any(HttpPut.class));
    }

    @Test
    public void testGetDocument() throws Exception {
        HttpResponse mockHttpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        InputStream content = new ByteArrayInputStream("{\"document\":\"some document\"}".getBytes());
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(content);
        when(client.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);

        final String document = slaveClient.getDocument("key");
        verify(client).execute(any(HttpGet.class));
        assertThat(document).isEqualTo("some document");
    }

    @Test
    public void testSearch() throws Exception {
        HttpResponse mockHttpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        InputStream content = new ByteArrayInputStream("{\"keys\":[\"key\"]}".getBytes());
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(content);
        when(client.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);

        final Collection<String> keys = slaveClient.search(Collections.singletonList("token"));
        verify(client).execute(any(HttpGet.class));
        assertThat(keys.size()).isEqualTo(1);
        assertThat(keys.iterator().next()).isEqualTo("key");
    }
}