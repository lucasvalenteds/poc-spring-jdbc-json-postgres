package com.example.vehicle;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    @Query("SELECT * FROM vehicle WHERE vehicle_details->>'type' = :type")
    List<Vehicle> findAllByType(@Param("type") String type);
}
