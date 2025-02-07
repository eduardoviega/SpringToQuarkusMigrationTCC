package com.todolist;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemListController {
    private static final Logger logger = Logger.getLogger(ItemListController.class);

    @Inject
    private ItemListService itemListService;

    @GET
    @Path("/getList")
    public Response findList() {
        List<ListItem> items = itemListService.findAll();
        return Response.ok(items).build();
    }

    @POST
    @Path("/addItem")
    @Consumes("application/json")
    public Response addItem(ListItem listItem) {
        try{
            int rowsAffected = itemListService.postItem(listItem);
            logger.info("Item inserido com sucesso!");
            return Response.accepted("Item inserido com sucesso!").build();
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao inserir o item:", e);
            return Response.serverError().entity("Ocorreu um erro ao inserir o item: " + e).build();
        }

    }

    @DELETE
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteItem(ListItem listItem) {
        try{
            int rowsAffected = itemListService.deleteItem(listItem);
            logger.info("Item deletado com sucesso!");
            return Response.accepted("Item deletado com sucesso!").build();
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao excluir o item:", e);
            return Response.serverError().entity("Ocorreu um erro ao excluir o item: " + e).build();
        }

    }

    @PUT
    @Path("/checkItem")
    @Consumes("application/json")
    public Response checkItem(Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        Boolean isChecked = (Boolean) requestBody.get("isChecked");

        if (name == null || isChecked == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetros inválidos.").build();
        }

        try {
            int rowsAffected = itemListService.checkItem(name, isChecked);
            logger.info("Item marcado/desmarcado com sucesso!");
            return Response.accepted("Item marcado/desmarcado com sucesso!").build();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao marcar/desmarcar o item:", e);
            return Response.serverError().entity("Ocorreu um erro ao marcar/desmarcar o item: " + e.getMessage()).build();
        }
    }

}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//        import jakarta.ws.rs.core.Response;
//import org.jboss.logging.Logger;
//
//import java.util.List;
//import java.util.Map;
//
//@Path("/items")
//public class ItemListController {
//    private static final Logger logger = Logger.getLogger(ItemListController.class);
//
//    @Inject
//    private ItemListService itemListService;
//
//    @GET
//    @Path("/getList")
//    public Response findList() {
//        List<ListItem> items = itemListService.findAll();
//        return Response.ok(items).build();
//    }
//
//    @POST
//    @Path("/addItem")
//    @Consumes("application/json")
//    public Response addItem(ListItem listItem) {
//        try{
//            int rowsAffected = itemListService.postItem(listItem);
//            logger.info("Item inserido com sucesso!");
//            return Response.accepted("Item inserido com sucesso!").build();
//        }catch(Exception e) {
//            logger.error("Ocorreu um erro ao inserir o item:", e);
//            return Response.serverError().entity("Ocorreu um erro ao inserir o item: " + e).build();
//        }
//
//    }
//
//    @DELETE
//    @Path("/delete")
//    @Consumes("application/json")
//    public Response deleteItem(ListItem listItem) {
//        try{
//            int rowsAffected = itemListService.deleteItem(listItem);
//            logger.info("Item deletado com sucesso!");
//            return Response.accepted("Item deletado com sucesso!").build();
//        }catch(Exception e) {
//            logger.error("Ocorreu um erro ao excluir o item:", e);
//            return Response.serverError().entity("Ocorreu um erro ao excluir o item: " + e).build();
//        }
//
//    }
//
//    @PUT
//    @Path("/checkItem")
//    @Consumes("application/json")
//    public Response checkItem(Map<String, Object> requestBody) {
//        String name = (String) requestBody.get("name");
//        Boolean isChecked = (Boolean) requestBody.get("isChecked");
//
//        if (name == null || isChecked == null) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetros inválidos.").build();
//        }
//
//        try {
//            int rowsAffected = itemListService.checkItem(name, isChecked);
//            logger.info("Item marcado/desmarcado com sucesso!");
//            return Response.accepted("Item marcado/desmarcado com sucesso!").build();
//        } catch (Exception e) {
//            logger.error("Ocorreu um erro ao marcar/desmarcar o item:", e);
//            return Response.serverError().entity("Ocorreu um erro ao marcar/desmarcar o item: " + e.getMessage()).build();
//        }
//    }
//
//}