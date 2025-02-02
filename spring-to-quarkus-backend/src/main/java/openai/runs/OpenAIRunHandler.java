package openai.runs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenAIRunHandler {

    private static final String THREAD_ID = "thread_bb87GZRlqth3bMCFIwaO5eaV";
    private static final String ASSISTANT_ID = "asst_9PDyKZZkEaHfBbzcsZ86vgsn";

    public static void main(String[] args) {
        String apiUrl = "https://api.openai.com/v1/threads/" + THREAD_ID + "/runs";
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + "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");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("OpenAI-Beta", "assistants=v2");
                connection.setDoOutput(true);

                String requestBody = """
                    {
                        "assistant_id": "%s",
                        "stream": true
                    }
                    """.formatted(ASSISTANT_ID);
                connection.getOutputStream().write(requestBody.getBytes());

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("data: ")) {
                            String eventData = line.substring(6).trim();
                            processEvent(eventData);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar a run: " + e.getMessage());
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    private static void processEvent(String eventJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(eventJson);
            } catch (Exception e) {
                System.out.println("Entrada inválida recebida: " + eventJson);
                return;
            }

            if (rootNode.isObject()) {
                if (rootNode.get("status") == null || !"completed".equals(rootNode.get("status").asText())) {
                    return;
                }
                if (rootNode.get("role") == null || !"assistant".equals(rootNode.get("role").asText())) {
                    return;
                }

                System.out.println("Evento recebido: " + eventJson);
            } else if (rootNode.isArray()) {
                System.out.println("Evento recebido como array: " + rootNode.toString());
            } else {
                System.out.println("Tipo inesperado de JSON recebido: " + rootNode.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
