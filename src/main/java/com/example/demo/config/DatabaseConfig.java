package com.example.demo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:h2:mem:test")
                .username("admin")
                .password("admin")
                .driverClassName("org.h2.Driver")
                .build();
    }
}


