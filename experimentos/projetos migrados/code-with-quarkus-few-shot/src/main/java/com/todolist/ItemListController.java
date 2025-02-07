package com.todolist;

import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("")
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
            return Response.status(Response.Status.ACCEPTED).entity("Item inserido com sucesso!").build();
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao inserir o item:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao inserir o item: " + e).build();
        }
    }

    @DELETE
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteItem(ListItem listItem) {
        try{
            int rowsAffected = itemListService.deleteItem(listItem);
            logger.info("Item deletado com sucesso!");
            return Response.status(Response.Status.ACCEPTED).entity("Item deletado com sucesso!").build();
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao excluir o item:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao excluir o item: " + e).build();
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
            return Response.status(Response.Status.ACCEPTED).entity("Item marcado/desmarcado com sucesso!").build();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao marcar/desmarcar o item:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao marcar/desmarcar o item: " + e.getMessage()).build();
        }
    }

}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import org.jboss.logging.Logger;
//import javax.inject.Inject;
//import javax.ws.rs.*;
//        import javax.ws.rs.core.Response;
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
//            return Response.status(Response.Status.ACCEPTED).entity("Item inserido com sucesso!").build();
//        }catch(Exception e) {
//            logger.error("Ocorreu um erro ao inserir o item:", e);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao inserir o item: " + e).build();
//        }
//    }
//
//    @DELETE
//    @Path("/delete")
//    @Consumes("application/json")
//    public Response deleteItem(ListItem listItem) {
//        try{
//            int rowsAffected = itemListService.deleteItem(listItem);
//            logger.info("Item deletado com sucesso!");
//            return Response.status(Response.Status.ACCEPTED).entity("Item deletado com sucesso!").build();
//        }catch(Exception e) {
//            logger.error("Ocorreu um erro ao excluir o item:", e);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao excluir o item: " + e).build();
//        }
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
//            return Response.status(Response.Status.ACCEPTED).entity("Item marcado/desmarcado com sucesso!").build();
//        } catch (Exception e) {
//            logger.error("Ocorreu um erro ao marcar/desmarcar o item:", e);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro ao marcar/desmarcar o item: " + e.getMessage()).build();
//        }
//    }
//
//}