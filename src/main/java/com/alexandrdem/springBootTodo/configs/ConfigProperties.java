package com.alexandrdem.springBootTodo.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
/**
 * @author AlexanderDementev on 04.09.2021
 */
@Configuration
@ConfigurationProperties(prefix = "todo")
@Data
public class ConfigProperties {
    private String syncUrl;
    private String syncBasePath;
}
