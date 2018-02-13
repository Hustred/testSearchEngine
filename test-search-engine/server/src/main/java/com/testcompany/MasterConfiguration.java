package com.testcompany;

import com.testcompany.model.DocumentBuilder;
import com.testcompany.properties.NetworkProperties;
import com.testcompany.web.service.MasterSearchService;
import com.testcompany.web.service.SearchService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Configuration
@Component
@Profile("master")
public class MasterConfiguration {

    @Bean
    @Primary
    public SearchService masterService(NetworkProperties networkProperties) {
        return new MasterSearchService(new HashMap<>(), new DocumentBuilder(), new SlaveClient(networkProperties.getSlave1(),
                HttpClientBuilder.create().build()), new SlaveClient(networkProperties.getSlave2(), HttpClientBuilder.create().build()));
    }
}
