# Migração de Spring Boot para Quarkus

## Principais mudanças realizadas durante a migração:

1. **Anotações de classe e injeção de dependências:**
    - Substituímos `@RestController`, `@Service` e `@Repository` do Spring por `@Path`, `@ApplicationScoped` e injeções diretas no Quarkus.
    - Mudamos a injeção de dependência de `@Autowired` para `@Inject`.

2. **Configurações:**
    - O arquivo `application.properties` foi ajustado para o formato necessário pelo Quarkus, configurando o datasource e as propriedades do Hibernate.

3. **Dependências:**
    - Alteramos o `pom.xml` para usar o `quarkus-bom` em vez do starter do Spring Boot.
    - Adicionamos dependências específicas do Quarkus para RESTEasy, Hibernate ORM com Panache, PostgreSQL, logging (Log4j2) e JSON-B.

4. **Classes Principais:**
    - Atualizamos o ponto de entrada da aplicação para usar `Quarkus.run()`.

## Dependências alteradas ou adicionadas no `pom.xml`:

- `io.quarkus:quarkus-resteasy`
- `io.quarkus:quarkus-hibernate-orm`
- `io.quarkus:quarkus-hibernate-orm-panache`
- `io.quarkus:quarkus-jdbc-postgresql`
- `io.quarkiverse.log4j2:quarkus-log4j2`
- `io.quarkus:quarkus-resteasy-jsonb`

## Soluções alternativas:
- Substituiu-se o uso do `JdbcTemplate` do Spring por o uso de `EntityManager` diretamente, visto que é o mais próximo para consultas dentro do contexto do Quarkus.