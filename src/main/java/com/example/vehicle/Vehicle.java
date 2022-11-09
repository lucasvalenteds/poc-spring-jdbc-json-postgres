package com.example.vehicle;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("vehicle")
@Data
public final class Vehicle {


    @Id
    @Column("vehicle_id")
    private Long id;

    @Column("vehicle_description")
    private String description;

    @Column("vehicle_details")
    private VehicleDetails details;
}
