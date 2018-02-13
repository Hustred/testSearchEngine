package com.testcompany;

import com.testcompany.model.DocumentBuilder;
import com.testcompany.web.service.SearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Configuration
@Component
public class SlaveConfiguration {

    @Bean
    public SearchService searchService(){
        return new SearchService(new HashMap<>(), new DocumentBuilder());
    }
}
