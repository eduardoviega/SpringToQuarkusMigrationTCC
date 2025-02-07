# To-Do List Application Migration to Quarkus

## Main Changes and Configurations
1. **Controllers**: Converted Spring MVC controllers to JAX-RS resource classes in Quarkus.
    - The `@RestController` and path annotations are replaced by JAX-RS `@Path`, `@GET`, `@POST`, `@DELETE`, and `@PUT`. The `ResponseEntity` from Spring is replaced by JAX-RS `Response`.

2. **Services**: Converted Spring `@Service` annotated classes to CDI beans using `@ApplicationScoped`.

3. **Repositories**:
    - Used Panache Repository capabilities in Quarkus for database access. The data access logic needs `PanacheRepository` or manual JDBC execution with `Agroal` datasource.
    - Note: The `JdbcTemplate` was initially used in Spring, which requires equivalent manual handling or entity management in Quarkus.

4. **Entity Management**:
    - Adjusted JPA entities to use `jakarta.persistence`. Annotations from Spring’s `data-jpa` such as `@Id` are removed when using basic JPA.

5. **Configuration**:
    - Spring `application.properties` were translated to Quarkus format (e.g., `quarkus.http.port`, `quarkus.datasource.*`, etc.).

6. **Logging**:
    - Logging adapted to use `org.jboss.logging.Logger` in Quarkus.

## Dependencies and Maven Changes
1. **POM Changes**:
    - Replaced the Spring Boot starter and parent dependencies with Quarkus BOM and core extensions like `resteasy`, `hibernate-orm-panache`, and `jdbc-postgresql`.

2. **Plugins**:
    - Spring Boot Maven plugin replaced with Quarkus Maven Plugin for building and running Quarkus applications.

3. **Lombok and Logging**:
    - Lombok remains appropriate for POJOs, and Log4j is retained for logging, requiring specific Quarkus adaptation if necessary.

## Special Notes
- The repository methods using `JdbcTemplate` need careful translation to Quarkus-compatible alternatives, ensuring SQL execution aligns with Quarkus's ORM and native query techniques.
- Validate that each transaction or entity management intersects effectively with the Quarkus lifecycle and request handling.