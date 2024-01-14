package ua.foxminded.carrest.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

    @Value("${rest-template.connect-timeout-seconds}")
    private Duration connectTimeout;

    @Value("${rest-template.read-timeout-seconds}")
    private Duration readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(connectTimeout)
            .setReadTimeout(readTimeout)
            .interceptors(new LoggingInterceptor())
            .build();
    }
}
