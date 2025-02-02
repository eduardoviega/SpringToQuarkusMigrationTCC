package openai.runs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class RunController {

    @ConfigProperty(name = "api-key")
    String API_KEY;

    public String createRun(String threadId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("assistant_id", "asst_9PDyKZZkEaHfBbzcsZ86vgsn");
        requestBodyMap.put("instructions", readPromptFile());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestBodyMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/threads/" + threadId + "/runs"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao criar a run: " + response.body());
        }

        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("id")) {
            return rootNode.get("id").asText();
        } else {
            throw new RuntimeException("Resposta inesperada: O campo 'id' não foi encontrado. Resposta: " + response.body());
        }
    }

    public String readPromptFile() {
        String content = readFromFileSystem();
        if (content != null) {
            return content;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    private String readFromFileSystem() {
        try {
            return new String(Files.readAllBytes(Paths.get("prompts/Custom-prompt.md")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRunResponse(String runId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/threads/thread_bb87GZRlqth3bMCFIwaO5eaV/runs/" + runId))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao consultar a run: " + response.body());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("content")) {
            JsonNode contentNode = rootNode.get("content");
            if (contentNode.isArray() && !contentNode.isEmpty()) {
                JsonNode message = contentNode.get(0);
                if (message.has("text")) {
                    return message.get("text").get("value").asText();
                }
            }
        }
        throw new RuntimeException("Resposta não encontrada na run. Detalhes: " + response.body());
    }
}
