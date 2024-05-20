package com.example.BFF1.WebBff.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogServiceConfiguration {

    private final LogRepository logRepository;

    public LogServiceConfiguration(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Bean
    public LogService logService() {
        return new LogService(logRepository);
    }
}