package com.example.internal;

import com.example.vehicle.VehicleDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public final class VehicleDetailsConverter {

    private VehicleDetailsConverter() {
    }

    @ReadingConverter
    static final class Reading extends JsonConverter.Reading<VehicleDetails> {
        public Reading(ObjectMapper objectMapper) {
            super(objectMapper, VehicleDetails.class);
        }
    }

    @WritingConverter
    static final class Writing extends JsonConverter.Writing<VehicleDetails> {
        public Writing(ObjectMapper objectMapper) {
            super(objectMapper);
        }
    }
}
