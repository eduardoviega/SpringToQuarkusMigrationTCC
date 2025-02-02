package openai.vectorstores;

import openai.files.FileController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VectorStoreController {

    @ConfigProperty(name = "api-key")
    String API_KEY;

    static final String API_URL = "https://api.openai.com/v1/vector_stores";

    @Inject
    FileController fileController;

    public String createVectorStore() throws Exception {
        String fileListResponse = fileController.listFiles();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode files = objectMapper.readTree(fileListResponse).get("data");

        List<String> fileIds = new ArrayList<>();
        for (JsonNode file : files) {
            String fileId = file.get("id").asText();
            fileIds.add(fileId);
        }

        VectorStoreRequest vectorStoreRequest = new VectorStoreRequest("test-vector-store", fileIds);
        return createVectorStore(vectorStoreRequest);
    }

    private String createVectorStore(VectorStoreRequest vectorStoreRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(vectorStoreRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("OpenAI-Beta", "assistants=v2")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Falha ao criar Vector Store: " + response.body());
        }

        return response.body();
    }
}
