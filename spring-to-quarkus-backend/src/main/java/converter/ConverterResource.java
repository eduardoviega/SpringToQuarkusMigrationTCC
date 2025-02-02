package converter;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/migrate")
public class ConverterResource {

    @Inject
    ConverterController converterController;

    @POST
    @Path("/files/{promptId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response migrateFiles(MultipartFormDataInput input, @PathParam("promptId") int promptId) {
        try {
            return converterController.migrateFiles(input, promptId);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao migrar arquivos: " + e.getMessage())
                    .build();
        }
    }
}
