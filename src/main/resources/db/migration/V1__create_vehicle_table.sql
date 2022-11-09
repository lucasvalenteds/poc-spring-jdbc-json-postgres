CREATE SEQUENCE vehicle_id_sequence START WITH 1;
CREATE TABLE vehicle
(
    vehicle_id          INTEGER DEFAULT nextval('vehicle_id_sequence'),
    vehicle_description VARCHAR,
    vehicle_details     JSONB,

    CONSTRAINT pk_vehicle_id PRIMARY KEY (vehicle_id)
);
