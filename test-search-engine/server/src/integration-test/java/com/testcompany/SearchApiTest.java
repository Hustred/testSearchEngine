package com.testcompany;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Server.class})
public class SearchApiTest {

    private MockMvc mvc;

    @Autowired
    public void setContext(final WebApplicationContext context)
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPutDocument() throws Exception {
        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("helloW", "Hello world document"))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isAccepted());
    }

    @Test
    public void testPutEmptyDocument() throws Exception {
        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("helloW", ""))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPutEmptyKey() throws Exception {
        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("", "Hello world document"))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetDocument() throws Exception {
        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("helloW", "Hello world document"))
                .contentType(MediaType.APPLICATION_JSON)
        );

        mvc.perform(get("/searchApi/documents/helloW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document", equalTo("Hello world document")));

        mvc.perform(get("/searchApi/documents/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document").doesNotExist());
    }

    @Test
    public void testSearch() throws Exception {
        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("helloW", "Hello, world"))
                .contentType(MediaType.APPLICATION_JSON)
        );

        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("goodbyeW", "Goodbye? world"))
                .contentType(MediaType.APPLICATION_JSON)
        );

        mvc.perform(put("/searchApi/documents")
                .content(buildDocumentPutRequest("helloK", "Hello: kitty"))
                .contentType(MediaType.APPLICATION_JSON)
        );

        mvc.perform(post("/searchApi/search")
                        .content(buildSearchRequest(new String[]{"Hello", "world"}))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys", hasSize(1)))
                .andExpect(jsonPath("$.keys[*]", hasItem("helloW")));

        mvc.perform(post("/searchApi/search")
                        .content(buildSearchRequest(new String[]{"Not", "present"}))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys", hasSize(0)));

        mvc.perform(post("/searchApi/search")
                        .content(buildSearchRequest(new String[]{"Hello"}))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys", hasSize(2)))
                .andExpect(jsonPath("$.keys[*]", hasItem("helloW")))
                .andExpect(jsonPath("$.keys[*]", hasItem("helloK")));

    }

    @Test
    public void testEmptySearch() throws Exception {
        mvc.perform(post("/searchApi/search")
                        .content(buildSearchRequest(null))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    private String buildDocumentPutRequest(String key, String document) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("key", key);
        fields.put("document", document);

        return new Gson().toJson(fields);
    }

    private String buildSearchRequest(String[] tokens) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("tokens", tokens);

        return new Gson().toJson(fields);
    }
}
