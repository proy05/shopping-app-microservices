package com.roypr.microservices.order.config;

import com.roypr.microservices.order.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {
        // 1. Build the RestClient and apply timeouts using the modern 3.4+ Builder API
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl) // Match your inventory service location
                .requestFactory(getClientRequestFactory())
                .build();

        // 2. Pass the configured RestClient to your Adapter
        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        // 3. Create the Factory
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // 4. Return the compiled proxy implementation
        return factory.createClient(InventoryClient.class);
    }

    // Modern Spring Boot 3.4+ configuration style to add timeouts
    private ClientHttpRequestFactory getClientRequestFactory() {
        // 1. Create the settings instance holding your durations
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(3));

        // 2. Pass those settings into the build method
        return ClientHttpRequestFactoryBuilder.detect()
                .build(settings);
    }

}
