package ru.thundercloud.thunder_cloud_loader_analize.config.resttemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author DRakovskiy
 */

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "rest.template")
public class RestTemplateConfig {

    private Integer connectTimeout;
    private Integer readTimeout;
    private String baseUrl;
    private String stockUrl;
    private String soldUrl;

}
