package com.example.demo.config;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Getter
@Setter
@Configuration
public class WeatherApiConfig {

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.api-key}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(1000000)
                .limitRefreshPeriod(Duration.ofDays(30))
                .timeoutDuration(Duration.ofMillis(50))
                .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.of(rateLimiterConfig());
    }

    @Bean
    public RateLimiter rateLimiter(@Qualifier("rateLimiterRegistry") RateLimiterRegistry rateLimiterRegistry) {
        return rateLimiterRegistry.rateLimiter("weatherApi");
    }
}