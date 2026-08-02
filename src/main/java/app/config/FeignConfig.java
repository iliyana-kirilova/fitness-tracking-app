package app.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${analytics.service.api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return requestTemplate ->
                requestTemplate.header("X-API-Key", apiKey);
    }
}
