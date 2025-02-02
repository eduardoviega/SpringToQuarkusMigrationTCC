package openai.assistants;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.net.http.HttpResponse;

@Path("/assistants")
public class AssistantResource {
    @Inject
    AssistantController assistantController;

    @GET
    @Path("/listAll")
    public Response listAll() {
        try {
            HttpResponse<String> response = assistantController.getAssistants();

            return Response.ok(response.body()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar assistentes: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/createMigrationAssistant")
    public Response createMigrationAssistant() {
        try {
            String assistantName = "Migration Assistant";
            String instructions = "Você é um especialista em frameworks Java e migração de projetos backend. Sua tarefa é realizar uma **migração completa** de um projeto Spring para o Quarkus. A conversão deve incluir **todas as classes e configurações necessárias**, garantindo que o código resultante esteja funcional e pronto para execução.";

            String assistantId = assistantController.createMigrationAssistant(assistantName, instructions);

            return Response.ok("Assistente criado com ID: " + assistantId).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar assistente: " + e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/addVectorStore/{assistantId}/{vectorStoreId}")
    public Response addVectorStore(@PathParam("assistantId") String assistantId, @PathParam("vectorStoreId") String vectorStoreId) {
        try {
            String response = assistantController.addVectorStore(assistantId, vectorStoreId);
            return Response.ok("Assitente editado com sucesso: " + response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao editar Assistente: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/createThread/{vectorStoreId}")
    public Response createThread(@PathParam("vectorStoreId") String vectorStoreId) {
        try {
            String threadResponse = assistantController.createThread(vectorStoreId);
            return Response.ok("Thread criada com sucesso: " + threadResponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar a thread: " + e.getMessage())
                    .build();
        }
    }
}