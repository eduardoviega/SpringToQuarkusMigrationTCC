# To-Do List Quarkus Migration

## Overview

This project was migrated from a Spring Boot application to Quarkus. The application serves as a basic REST API to manage a to-do list with operations for adding, deleting, listing, and checking items.

## Major Changes

1. **Annotations and Framework:**
    - Converted Spring-specific annotations to CDI (Contexts and Dependency Injection) used in Quarkus, such as replacing `@RestController` with JAX-RS annotations (`@Path`, `@GET`, `@POST`, etc.), and `@Service`/`@Repository` with `@ApplicationScoped`.
    - Replaced `@Autowired` with `@Inject` for dependency injection.

2. **Logging:**
    - Updated logging from Log4j to Quarkus built-in logger (`Logger` from `org.jboss.logging`).

3. **JPA and Hibernate:**
    - Utilized Quarkus Panache for JPA operations, replacing standard JPA repositories and service patterns.

4. **Configuration:**
    - Configuration is moved from `application.properties` to `application.properties` with Quarkus-specific properties.
    - Updated database configuration properties to Quarkus style.

5. **Build System:**
    - Updated Maven build configuration to use Quarkus BOM and replace Spring Boot plugins with the Quarkus Maven plugin.

## Dependencies

Replaced Spring Boot dependencies with Quarkus equivalents:

- `spring-boot-starter-data-jpa`, `spring-boot-starter-web` with `quarkus-resteasy`, `quarkus-hibernate-orm-panache`, `quarkus-resteasy-jsonb`.
- PostgreSQL driver remains the same but configured for Quarkus (`quarkus-jdbc-postgresql`).

## Running the Application

To run the application, execute:

```
./mvnw compile quarkus:dev
```

This will start the application in development mode, listening on port `8081` as specified in the configuration file.

## Testing

Unit tests and endpoint tests are set up using JUnit 5 and Rest Assured as integrated testing tools with Quarkus.

## Further Notes

- Ensure that you have a PostgreSQL instance running and accessible with the given configuration.
- Additional configurations might be needed to suit production requirements specifically for security and advanced features not covered directly in this migration.