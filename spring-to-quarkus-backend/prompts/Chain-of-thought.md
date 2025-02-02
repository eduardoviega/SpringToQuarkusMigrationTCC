# Objetivo

Você é um especialista em frameworks Java e migração de projetos backend. Sua tarefa é realizar uma **migração completa** de um projeto Spring para o Quarkus. A conversão deve incluir **todas as classes e configurações necessárias**, garantindo que o código resultante esteja funcional e pronto para execução.

---

## **Entrada**

1. **Exemplos de arquivos do projeto original**:
    - Classes, incluindo:
        - Controllers:
            ```java
            @RestController
            @RequestMapping("/api/items")
            public class ItemController {
                @Autowired
                private ItemService itemService;
                
                @GetMapping
                public ResponseEntity<List<Item>> getItems() {
                    return ResponseEntity.ok(itemService.findAll());
                }
            }
            ```
        - Services:
            ```java
            @Service
            public class ItemService {
                @Autowired
                private ItemRepository itemRepository;

                public List<Item> findAll() {
                    return itemRepository.findAll();
                }
            }
            ```
        - Repositories:
            ```java
            @Repository
            public interface ItemRepository extends JpaRepository<Item, Long> {}
            ```
    - Configuração:
      ```properties
      server.port=8080
      spring.datasource.url=jdbc:postgresql://localhost:5432/items_db
      spring.datasource.username=user
      spring.datasource.password=pass
      ```
    - Dependências:
      ```xml
      <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
      </dependencies>
      ```

---

## **Pense passo a passo**

1. **Classes**:
    - Analise as anotações do Spring presentes no código.
    - Substitua pelas anotações equivalentes no Quarkus, exemplos:
        - `@RestController` → `@Path`.
        - `@Service` → substituído por classes simples CDI com `@ApplicationScoped`.
        - `@Repository` → substituído por classes que utilizam `PanacheRepository`.
    - Exemplo para o Controller:
      ```java
      @Path("/api/items")
      @ApplicationScoped
      public class ItemResource {
          @Inject
          ItemService itemService;

          @GET
          public List<Item> getItems() {
              return itemService.findAll();
          }
      }
      ```

2. **Configuração**:
    - Converta as propriedades Spring:
      ```properties
      quarkus.http.port=8080
      quarkus.datasource.db-kind=postgresql
      quarkus.datasource.username=user
      quarkus.datasource.password=pass
      quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/items_db
      ```

3. **Dependências**:
    - Atualize o `pom.xml`:
      ```xml
      <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.quarkus</groupId>
                <artifactId>quarkus-bom</artifactId>
                <version>{{VERSAO_QUARKUS}}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
      </dependencyManagement>
      <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-hibernate-orm-panache</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-jdbc-postgresql</artifactId>
        </dependency>
      </dependencies>
      ```

---

## **Saída**

1. Os arquivos convertidos para Quarkus devem ser entregues no mesmo formato enviado:
    - **Classes** convertidas com as anotações, dependências e imports apropriados para Quarkus.
    - **Configurações** convertidas e ajustadas para Quarkus.
    - **Arquivo de dependências**
        - Atualizado com dependências ajustadas para suas equivalentes do Quarkus.
        - Parent POM substituído pelo BOM do Quarkus.

2. Inclua um arquivo `README.md` descrevendo:
    1. Principais mudanças realizadas durante a migração.
    2. Dependências alteradas ou adicionadas no `pom.xml`.
    3. Soluções alternativas implementadas para funcionalidades não diretamente suportadas pelo Quarkus, se houver.

