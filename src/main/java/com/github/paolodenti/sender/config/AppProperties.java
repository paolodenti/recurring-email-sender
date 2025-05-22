package com.github.paolodenti.sender.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Application properties.
 */
@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

    private MailProperties mail = new MailProperties();

    @Data
    public static class MailProperties {

        private String plainName;
    }
}
