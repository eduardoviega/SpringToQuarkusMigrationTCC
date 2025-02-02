package converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class FileUtils {

    public String getFileName(InputPart inputPart) {
        try {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String contentDisposition = headers.getFirst("Content-Disposition");

            if (contentDisposition != null && contentDisposition.contains("filename=")) {
                return contentDisposition
                        .split("filename=")[1]
                        .trim()
                        .replaceAll("\"", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "arquivo_desconhecido";
    }

    public String getFileExtension(InputPart inputPart) {
        String fileName = getFileName(inputPart);
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }
        return "extensão_desconhecida";
    }

    public String extractFileContent(InputStream fileStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler arquivo";
        }
    }

    public String readPromptFile(int promptId) {
        String content = readFromFileSystem(promptId);
        if (content != null) {
            return content;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    private String readFromFileSystem(int promptId) {
        try {
            String fileName = TecnicaPromptEnum.getFileNameById(promptId);
            return new String(Files.readAllBytes(Paths.get("prompts/"+fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
