package ru.thundercloud.thunder_cloud_loader_analize.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.thundercloud.thunder_cloud_loader_analize.config.resttemplate.RestTemplateConfig;
import ru.thundercloud.thunder_cloud_loader_analize.exception.handlers.RestTemplateResponseErrorHandler;

/**
 *
 * @author DRakovskiy
 */

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final RestTemplateConfig settings;
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(settings.getBaseUrl());
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(settings.getConnectTimeout());
        factory.setReadTimeout(settings.getReadTimeout());

        return new RestTemplateBuilder()
                .uriTemplateHandler(uriBuilderFactory)
                .requestFactory(() -> factory)
                .errorHandler(new RestTemplateResponseErrorHandler(objectMapper))
                .build();
    }
}

