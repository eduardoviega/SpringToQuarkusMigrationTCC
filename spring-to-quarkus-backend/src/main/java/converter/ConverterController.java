package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ConverterController {

    @ConfigProperty(name = "api-key")
    String API_KEY;

    @ConfigProperty(name = "chat-model-name")
    String CHAT_MODEL_NAME;

    @Inject
    FileUtils fileUtils;

    public Response migrateFiles(MultipartFormDataInput input, int promptId) {
        List<Message> chatContext = new ArrayList<>();
        setPromptContext(chatContext, promptId);

        Map<String, String> fileProperties = new LinkedHashMap<>();
        try {
            setFilesPrompt(input, fileProperties, chatContext);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar os arquivos").build();
        }
        Map<String, Object> responseFormat = createResponseFormat(fileProperties);

        String response = callOpenAI(chatContext, responseFormat);
        // String response = RespostasTecnicasEnum.getProjectResponseById(promptId);
        // System.out.println(response);
        return Response.ok(response).build();
    }

    private void setPromptContext(List<Message> chatContext, int promptId) {
        boolean isPromptChaining = TecnicaPromptEnum.getFileNameById(promptId).equals(TecnicaPromptEnum.PROMPT_CHAINING.getFileName());
        if (isPromptChaining) {
            String promptCompleto = fileUtils.readPromptFile(promptId);
            String[] etapasPrompt = promptCompleto.split("---");
            for (String etapa : etapasPrompt) {
                chatContext.add(new Message("system", etapa.trim()));
            }
        } else {
            chatContext.add(new Message("system", fileUtils.readPromptFile(promptId)));
        }
    }

    private void setFilesPrompt(MultipartFormDataInput input, Map<String, String> fileProperties, List<Message> chatContext) throws IOException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> fileParts = uploadForm.get("files");

        for (InputPart inputPart : fileParts) {
            InputStream fileStream = inputPart.getBody(InputStream.class, null);
            String fileName = fileUtils.getFileName(inputPart);
            String fileExtension = fileUtils.getFileExtension(inputPart);
            String fileContent = fileUtils.extractFileContent(fileStream);
            fileProperties.put(fileName, "string");
            chatContext.add(new Message(
                    "user",
                    "Aqui está o arquivo `"+fileName+"` para migração:\n``` "+fileExtension+"\n"+fileContent.trim()+"\n```"
            ));
        }
    }

    private Map<String, Object> createResponseFormat(Map<String, String> fileProperties) {
        Map<String, Object> schema = new LinkedHashMap<>();

        fileProperties.put("README.md", "string");
        Map<String, Object> properties = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : fileProperties.entrySet()) {
            Map<String, String> fileDetails = new LinkedHashMap<>();
            fileDetails.put("type", "string");
            properties.put(entry.getKey(), fileDetails);
        }

        schema.put("type", "object");
        schema.put("properties", properties);
        schema.put("required", new ArrayList<>(fileProperties.keySet()));
        schema.put("additionalProperties", false);

        Map<String, Object> jsonSchema = new LinkedHashMap<>();
        jsonSchema.put("name", "arquivos_migrados");
        jsonSchema.put("strict", true);
        jsonSchema.put("schema", schema);

        Map<String, Object> responseFormat = new LinkedHashMap<>();
        responseFormat.put("type", "json_schema");
        responseFormat.put("json_schema", jsonSchema);

        return responseFormat;
    }

    private String callOpenAI(List<Message> messages, Map<String, Object> responseFormat) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(
                    Map.of(
                            "model", CHAT_MODEL_NAME,
                            "messages", messages,
                            "response_format", responseFormat
                    )
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao chamar a API: " + e.getMessage();
        }
    }
}
