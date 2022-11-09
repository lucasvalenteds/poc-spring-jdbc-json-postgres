package com.example.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = VehicleDetails.BICYCLE_TYPE, value = VehicleDetails.BicycleDetails.class),
        @JsonSubTypes.Type(name = VehicleDetails.CAR_TYPE, value = VehicleDetails.CarDetails.class),
})
public interface VehicleDetails {

    String BICYCLE_TYPE = "BICYCLE";
    String CAR_TYPE = "CAR";

    String getType();

    @Data
    final class BicycleDetails implements VehicleDetails {
        @JsonIgnore
        private final String type = VehicleDetails.BICYCLE_TYPE;

        private Integer size;
        private String color;
    }

    @Data
    final class CarDetails implements VehicleDetails {
        @JsonIgnore
        private final String type = VehicleDetails.CAR_TYPE;

        @JsonProperty("vin")
        private String vehicleIdentifierNumber;
        private Integer productionYear;
    }
}
