package com.testcompany.model;

import org.junit.Test;

import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;

public class DocumentBuilderTest {

    private DocumentBuilder documentBuilder = new DocumentBuilder();

    @Test
    public void testRemovingOfPunctuation() throws Exception {
        String str = "This, -/ is #! an $ % ^ & * example ;: {} of a = -_ string with `~)() punctuation";
        final Document document = documentBuilder.generateDocument(str);
        assertThat(document).isNotNull();
        assertThat(document.getDocument()).isEqualTo(str);
        assertThat(document.getTokens()).containsAll(Arrays.asList("This", "is", "an", "example", "of", "a", "string", "with", "punctuation"));
    }
}