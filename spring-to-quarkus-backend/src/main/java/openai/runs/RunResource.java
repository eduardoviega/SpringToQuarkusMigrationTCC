package openai.runs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/runs")
public class RunResource {

    @Inject
    RunController runController;

    @POST
    @Path("/createRun/{threadId}")
    public Response createRun(@PathParam("threadId") String threadId) {
        try {
            String runId = runController.createRun(threadId);
            return Response.ok("Run criada com sucesso: " + runId).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar a run: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/response/{runId}")
    public Response getRunResponse(@PathParam("runId") String runId) {
        try {
            String response = runController.getRunResponse(runId);
            return Response.ok("Resposta do assistente: " + response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a resposta da run: " + e.getMessage())
                    .build();
        }
    }
}
