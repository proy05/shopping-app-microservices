package com.roypr.microservices.order.config;

import com.roypr.microservices.order.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

        @Bean
        public InventoryClient inventoryClient() {

            RestClient restClient = RestClient.create(inventoryServiceUrl);
            RestClientAdapter adapter = RestClientAdapter.create(restClient);
            HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

            InventoryClient inventoryClient = factory.createClient(InventoryClient.class);

            return inventoryClient;
        }
}
