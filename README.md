# POC: Spring Data JDBC JSON

It demonstrates how to persist and query polymorphic JSON in Postgres database.

We want to persist vehicle information into a relational database. Different types of vehicles have different
information, but we are going to use the same table for every type specific details of each vehicle. For example,
bicycles have a size and color, but cars have production year and Vehicle Identification Number (VIN).

The implementation should be type-safe and the database schema must store the vehicle details in a column of
type `JSONB` (binary JSON). We must define two generic abstract implementations of `Convert` interface from Spring Data
JDBC that use Jackson to serialize and deserialize the Java type before persisting or reading the value from the
database table column. Zero if statements should be used to do that, we should depend on the polymorphism support of the
serialization library.

The source code does not contain production code, only automated tests executed by JUnit with a database provisioned by
Testcontainers and database migrations managed by Flyway.

## How to run

| Description | Command          |
|-------------|------------------|
| Run tests   | `./gradlew test` |

## Preview

Examples of vehicle serialized to JSON:

```json
{
  "id": 1,
  "description": "Regular bicycle",
  "details": {
    "type": "BICYCLE",
    "size": 29,
    "color": "blue"
  }
}
```

```json
{
  "id": 2,
  "description": "Electric car",
  "details": {
    "type": "CAR",
    "productionYear": 2012,
    "vin": "WBAHD5313MBF95736"
  }
}
```
