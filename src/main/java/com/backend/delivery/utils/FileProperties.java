package com.backend.delivery.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
public class FileProperties {
    
    public String uploadDir;
}
