package com.example;

import com.example.vehicle.Vehicle;
import com.example.vehicle.VehicleDetails;
import com.example.vehicle.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);

    @Container
    private static final PostgreSQLContainer<?> CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    private static void setApplicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;

    @AfterEach
    public void afterEach(ApplicationContext context) throws JsonProcessingException {
        final var json = context.getBean(ObjectMapper.class)
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(vehicle);

        LOGGER.atInfo().log(json);
    }

    @Test
    void creatingBicycle() {
        final var bicycleDetails = new VehicleDetails.BicycleDetails();
        bicycleDetails.setSize(29);
        bicycleDetails.setColor("blue");

        final var bicycle = new Vehicle();
        bicycle.setDescription("Regular bicycle");
        bicycle.setDetails(bicycleDetails);
        vehicleRepository.save(bicycle);

        vehicle = vehicleRepository.findById(1L).orElseThrow();

        assertThat(vehicle.getId()).isEqualTo(1L);
        assertThat(vehicle.getDescription()).isEqualTo(bicycle.getDescription());
        assertThat(vehicle.getDetails())
                .isNotNull()
                .isInstanceOf(VehicleDetails.class)
                .isInstanceOf(VehicleDetails.BicycleDetails.class)
                .isNotInstanceOf(VehicleDetails.CarDetails.class);

        final var details = (VehicleDetails.BicycleDetails) vehicle.getDetails();
        assertThat(details.getType()).isEqualTo(VehicleDetails.BICYCLE_TYPE);
        assertThat(details.getSize()).isEqualTo(details.getSize());
        assertThat(details.getColor()).isEqualTo(details.getColor());
    }

    @Test
    void creatingCar() {
        final var carDetails = new VehicleDetails.CarDetails();
        carDetails.setVehicleIdentifierNumber("WBAHD5313MBF95736");
        carDetails.setProductionYear(2012);

        final var car = new Vehicle();
        car.setDescription("Electric car");
        car.setDetails(carDetails);
        vehicleRepository.save(car);

        vehicle = vehicleRepository.findById(2L).orElseThrow();

        assertThat(vehicle.getId()).isEqualTo(2L);
        assertThat(vehicle.getDescription()).isEqualTo(car.getDescription());
        assertThat(vehicle.getDetails())
                .isNotNull()
                .isInstanceOf(VehicleDetails.class)
                .isInstanceOf(VehicleDetails.CarDetails.class)
                .isNotInstanceOf(VehicleDetails.BicycleDetails.class);

        final var details = (VehicleDetails.CarDetails) vehicle.getDetails();
        assertThat(details.getType()).isEqualTo(VehicleDetails.CAR_TYPE);
        assertThat(details.getVehicleIdentifierNumber()).isEqualTo(details.getVehicleIdentifierNumber());
        assertThat(details.getProductionYear()).isEqualTo(details.getProductionYear());
    }

    @Test
    void findingVehicleByType() {
        final var bicycles = vehicleRepository.findAllByType(VehicleDetails.BICYCLE_TYPE);
        assertThat(bicycles)
                .hasSize(1)
                .first()
                .extracting(Vehicle::getDescription).isEqualTo("Regular bicycle");

        final var cars = vehicleRepository.findAllByType(VehicleDetails.CAR_TYPE);
        assertThat(cars).hasSize(1)
                .first()
                .extracting(Vehicle::getDescription).isEqualTo("Electric car");

        final var trucks = vehicleRepository.findAllByType("TRUCK");
        assertThat(trucks).isEmpty();
    }
}
