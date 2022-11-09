package com.example.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfiguration extends AbstractJdbcConfiguration {

    private final ObjectMapper objectMapper;

    @Override
    protected List<?> userConverters() {
        return List.of(
        );
    }
}
