# Migração de Spring Boot para Quarkus

## Principais Mudanças Realizadas Durante a Migração

1. **Configuração dos Beans e Injeção de Dependência**:
    - Substituímos `@Autowired` por injeção de dependência do Quarkus usando `@Inject`.
    - As anotações relacionadas aos componentes Spring, como `@RestController`, `@Service`, e `@Repository`, foram removidas e, quando necessário, substituídas por anotações equivalentes, como `@Path` e `@ApplicationScoped` do Quarkus.

2. **Configuração REST**:
    - Utilizamos `io.quarkus:quarkus-resteasy` para fornecer endpoints RESTful, substituindo as anotações Spring como `@GetMapping`, `@PostMapping`, etc., por `@GET`, `@POST`, e assim por diante.

3. **Persistência de Dados**:
    - A configuração do `DataSource` foi migrada para as propriedades do Quarkus no arquivo `application.properties`.
    - Usamos `EntityManager` para execuções de queries, substituindo `JdbcTemplate`.

4. **Uso de Logger**:
    - Substituímos `org.apache.logging.log4j.LogManager` por `org.jboss.logging.Logger`, que é mais alinhado ao stack do Quarkus.

## Dependências Alteradas ou Adicionadas no `pom.xml`

- Remoção das dependências do Spring Boot e substituição por Quarkus BOM.
- Adição das seguintes dependências:
    - `io.quarkus:quarkus-resteasy`
    - `io.quarkus:quarkus-hibernate-orm`
    - `io.quarkus:quarkus-jdbc-postgresql`
    - `io.quarkus:quarkus-junit5` para testes
    - `io.rest-assured:rest-assured` para suporte a testes REST

## Soluções Alternativas Implementadas

- **Persistência**: Em vez de `JdbcTemplate`, utilizamos `EntityManager` para operações de banco de dados.
- **Mecanismo de logging**: Como o Quarkus utiliza nativamente o JBoss Logging, utilizamos essa API em vez do Log4j.
- **Configuração de aplicação main**: O ponto de entrada foi adaptado utilizando `QuarkusMain`, que substitui a classe principal do Spring Boot.

A aplicação resultante está otimizada para rodar no Quarkus, utilizando suas vantagens em performance e rapidez no tempo de iniciação. Certifique-se de atualizar a versão do Quarkus para a versão desejada no arquivo `pom.xml`. O projeto está pronto para execução usando o comando `mvn quarkus:dev` para modo de desenvolvimento. 