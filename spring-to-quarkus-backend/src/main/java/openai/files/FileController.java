package openai.files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FileController {

    @ConfigProperty(name = "api-key")
    String API_KEY;

    static final String API_URL = "https://api.openai.com/v1/files";

    static final String UPLOAD_DIR = "/tmp/uploads";

    private static final List<String> ALLOWED_EXTENSIONS = List.of("c", "cpp", "css", "csv", "doc", "docx", "gif", "go", "html", "java", "jpeg", "jpg", "js", "json", "md", "pdf", "php", "pkl", "png", "pptx", "py", "rb", "tar", "tex", "ts", "txt", "webp", "xlsx", "zip");

    public String listFiles() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar arquivos: " + response.body());
        }

        return response.body();
    }

    public void deleteAllFiles(String fileListResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode files = objectMapper.readTree(fileListResponse).get("data");

        for (JsonNode file : files) {
            String fileId = file.get("id").asText();
            deleteFile(fileId);
        }
    }

    private void deleteFile(String fileId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + fileId))
                .header("Authorization", "Bearer " + API_KEY)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Falha ao excluir o arquivo " + fileId + ": " + response.body());
        }

        System.out.println("Arquivo " + fileId + " excluído com sucesso!");
    }

    public String uploadFiles(List<InputPart> fileParts) throws Exception {
        Path uploadDir = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        for (InputPart filePart : fileParts) {
            InputStream fileInputStream = filePart.getBody(InputStream.class, null);

            String fileName = filePart.getHeaders().getFirst("Content-Disposition").split("filename=")[1].replaceAll("\"", "");
            String extension = getFileExtension(fileName);

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                fileName = fileName.split("\\.")[0] + ".txt";
                convertToTxt(fileInputStream, uploadDir.resolve(fileName));
            } else {
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(fileInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            uploadFileToOpenAI(uploadDir.resolve(fileName));
        }

        return "Arquivos enviados com sucesso!";
    }

    private void convertToTxt(InputStream inputStream, Path outputPath) throws IOException {
        String content = new String(inputStream.readAllBytes());
        Files.write(outputPath, content.getBytes());
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    private void uploadFileToOpenAI(Path filePath) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String boundary = "----WebKitFormBoundary" + UUID.randomUUID();

        StringBuilder body = new StringBuilder();
        body.append("--").append(boundary).append("\r\n");
        body.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(filePath.getFileName()).append("\"\r\n");
        body.append("Content-Type: application/octet-stream\r\n\r\n");

        byte[] fileBytes = Files.readAllBytes(filePath);
        body.append(new String(fileBytes));

        body.append("\r\n--").append(boundary).append("\r\n");

        body.append("Content-Disposition: form-data; name=\"purpose\"\r\n\r\n");
        body.append("assistants");

        body.append("\r\n--").append(boundary).append("--\r\n");

        byte[] bodyBytes = body.toString().getBytes();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(bodyBytes))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Falha ao enviar arquivo para o OpenAI: " + response.body());
        }

        System.out.println("Arquivo enviado com sucesso: " + response.body());
    }
}
