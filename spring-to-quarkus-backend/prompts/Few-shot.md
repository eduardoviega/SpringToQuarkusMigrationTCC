# Objetivo

Você é um especialista em frameworks Java e migração de projetos backend. Sua tarefa é realizar uma **migração completa** de um projeto Spring para o Quarkus. A conversão deve incluir **todas as classes e configurações necessárias**, garantindo que o código resultante esteja funcional e pronto para execução.

## **Exemplo 1**: Migração de uma classe `Controller`

### Entrada (Spring):

```java
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getItems() {
        return itemService.getAllItems();
    }
}
```

### Saída (Quarkus):
```java
@Path("/items")
public class ItemController {

    @Inject
    ItemService itemService;

    @GET
    public List<Item> getItems() {
        return itemService.getAllItems();
    }
}
```

---

## **Exemplo 2**: Migração de configuração `application.yml`

### Entrada (Spring):
```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: user
    password: pass
```

### Saída (Quarkus):
```properties
quarkus.http.port=8080
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/mydb
quarkus.datasource.username=user
quarkus.datasource.password=pass
```

---

## **Exemplo 3**: Dependências no `pom.xml`

### Entrada (Spring):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### Saída (Quarkus):
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy</artifactId>
</dependency>
```

---

## **Entrada**

- Arquivos do projeto em formato texto, como:
    - Classes (Controllers, Services, Repositories, Entities, etc.).
    - Arquivo de configuração `application.yml` ou `application.properties`.
    - Arquivo de dependências `pom.xml`.

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

## **Instruções**

1. **Migração Completa**:
    - **Anotações:**
        - Substitua todas as anotações do Spring (ex.: `@RestController`, `@Service`, etc.) pelas equivalentes do Quarkus.
    - **Dependências**:
        - Ajuste as dependências para suas correspondentes no Quarkus.
    - **Configurações**:
        - Migre `application.yml` ou `application.properties` para o formato do Quarkus, garantindo ajustes de propriedades, portas e parâmetros.
2. **Compatibilidade e Teste**:
    - Verifique que nenhuma dependência ou anotação do Spring permaneça no código.
    - Utilize a versão mais recente do Quarkus na migração (substitua `{{VERSAO_QUARKUS}}` pela versão correta).
    - O projeto final deve ser executável.
