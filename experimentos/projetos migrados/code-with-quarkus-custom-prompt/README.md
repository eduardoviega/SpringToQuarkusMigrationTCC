# Projeto de Migração de Spring Boot para Quarkus

## Principais Mudanças Realizadas

- **Controladores e Serviços**: As anotações do Spring (`@RestController`, `@Service`, `@Autowired`) foram substituídas por equivalentes do Quarkus, como `@Path`, `@GET`, `@POST`, `@ApplicationScoped` e `@Inject`.
- **Repositórios**: Removido o uso do Spring Data JPA em favor de um repositório baseado em `JdbcTemplate` com Quarkus.
- **Entidade**: Anotações do JPA foram mantidas, mas o uso de `@Id` estava incorreto e foi corrigido para o padrão correto do JPA utilizando apenas anotações da `jakarta.persistence`.
- **Configurações**: As configurações do Spring datasource foram migradas para o formato do Quarkus no arquivo `application.properties`.
- **Pom.xml**: Substituição do `spring-boot-starter-parent` pelo Quarkus BOM. As dependências de Spring foram trocadas pelas de Quarkus, e ajustes foram feitos nas dependências do banco de dados e do logging.

## Dependências Alteradas ou Adicionadas no `pom.xml`

- **Quarkus RESTEasy** para construção de APIs REST em vez do Spring Boot Web.
- **Quarkus Hibernate ORM** para manipulação de JPA.
- **Quarkus JDBC PostgreSQL** para integração com o banco de dados PostgreSQL.
- **Lombok**: continua sendo usada, mas marcada como `provided`, pois é apenas para compilação.
- **JBoss LogManager** para suporte ao logging no Quarkus.

## Soluções Alternativas Implementadas

- **Logging**: Usado `Logger` do JBoss em substituição ao `Log4j` para integrar com o ambiente padrão de logs do Quarkus.

---

## Instruções para Executar o Projeto
1. Certifique-se de ter o Java JDK 17 instalado.
2. Atualize a versão do Quarkus no `pom.xml` substituindo `{{VERSAO_QUARKUS}}` pela última versão estável.
3. Execute o projeto utilizando o Maven:

```bash
./mvnw quarkus:dev
```

Isso iniciará o servidor embutido do Quarkus na porta 8081 conforme definido no `application.properties`. A API REST está acessível nos endpoints especificados no controlador `ItemListController`.

**Nota**: Certifique-se de que seu banco de dados PostgreSQL esteja configurado corretamente e acessível para os detalhes de conexão fornecidos no `application.properties`.