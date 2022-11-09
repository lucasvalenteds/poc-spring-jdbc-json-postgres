package com.example.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;

import java.sql.SQLException;

public final class JsonConverter {

    private JsonConverter() {
    }

    @RequiredArgsConstructor
    public static abstract class Reading<T> implements Converter<PGobject, T> {

        private final ObjectMapper objectMapper;
        private final Class<T> type;

        @Override
        public final T convert(PGobject source) {
            try {
                return objectMapper.readValue(source.getValue(), type);
            } catch (JsonProcessingException exception) {
                throw new RuntimeException("Error deserializing object to JSON", exception);
            }
        }
    }

    @RequiredArgsConstructor
    public static abstract class Writing<T> implements Converter<T, PGobject> {

        private final ObjectMapper objectMapper;

        @Override
        public final PGobject convert(T source) {
            try {
                final var json = objectMapper.writeValueAsString(source);

                final var object = new PGobject();
                object.setType("jsonb");
                object.setValue(json);

                return object;
            } catch (JsonProcessingException exception) {
                throw new RuntimeException("Error serializing object to JSON", exception);
            } catch (SQLException exception) {
                throw new RuntimeException("Error assigning JSON to Postgres type", exception);
            }
        }
    }
}
