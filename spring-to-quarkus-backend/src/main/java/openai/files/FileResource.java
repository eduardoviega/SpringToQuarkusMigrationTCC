package openai.files;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;
import java.util.Map;

@Path("/files")
public class FileResource {

    @Inject
    FileController fileController;

    @GET
    @Path("/listAll")
    @Produces("application/json")
    public Response listFiles() {
        try {
            String fileList = fileController.listFiles();

            return Response.status(Response.Status.OK).entity(fileList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar arquivos: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/deleteAll")
    @Produces("application/json")
    public Response deleteAllFiles() {
        try {
            String fileList = fileController.listFiles();

            fileController.deleteAllFiles(fileList);

            return Response.status(Response.Status.OK).entity("Todos os arquivos foram deletados com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir os arquivos: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFiles(MultipartFormDataInput input) {
        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            List<InputPart> fileParts = uploadForm.get("files");

            String response = fileController.uploadFiles(fileParts);

            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao fazer upload dos arquivos: " + e.getMessage())
                    .build();
        }
    }
}
