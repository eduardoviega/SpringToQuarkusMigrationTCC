package openai.vectorstores;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/vector-store")
public class VectorStoreResource {

    @Inject
    VectorStoreController vectorStoreController;

    @POST
    @Path("/create")
    public Response createVectorStore() {
        try {
            String response = vectorStoreController.createVectorStore();
            return Response.ok("Vector Store criado com sucesso: " + response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar Vector Store: " + e.getMessage())
                    .build();
        }
    }
}
