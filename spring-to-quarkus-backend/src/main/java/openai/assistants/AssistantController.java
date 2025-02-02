package openai.assistants;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class AssistantController {

    @ConfigProperty(name = "api-key")
    String API_KEY;

    static final String API_URL = "https://api.openai.com/v1/assistants";

    public HttpResponse<String> getAssistants() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String createMigrationAssistant(String name, String instructions) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("""
            {
              "name": "%s",
              "instructions": "%s",
              "model": "gpt-3.5-turbo",
              "tools": [{"type": "file_search"}],
              "response_format": {
                "type": "text"
              }
            }
            """, name, instructions);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Resposta da API: " + response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("id")) {
            return rootNode.get("id").asText();
        } else {
            throw new RuntimeException("Resposta inesperada: O campo 'id' não foi encontrado. Resposta: " + response.body());
        }
    }

    public String addVectorStore(String assistantId, String vectorStoreId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("""
        {
          "tool_resources": {
            "file_search": {
              "vector_store_ids": ["%s"]
            }
          }
        }
        """, vectorStoreId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + assistantId))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao editar assistente: " + response.body());
        }

        return response.body();
    }

    public String createThread(String vectorStoreId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("""
            {
              "tool_resources": {
                "file_search": {
                  "vector_store_ids": ["%s"]
                }
              }
            }
            """, vectorStoreId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/threads"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao criar a thread: " + response.body());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("id")) {
            return rootNode.get("id").asText();
        } else {
            throw new RuntimeException("Resposta inesperada: O campo 'id' não foi encontrado. Resposta: " + response.body());
        }
    }
}
