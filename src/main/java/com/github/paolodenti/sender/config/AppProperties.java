package com.github.paolodenti.sender.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

        private String from;
        private String plainName;
        private long delay;
        private String title;
        private String body;
        private List<String> to = new ArrayList<>();
    }
}
