package com.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;


@RestController
public class ItemListController {
    private static final Logger logger = LogManager.getLogger(ItemListController.class);

    @Autowired
    private ItemListService itemListService;

    @GetMapping("/getList")
    public ResponseEntity<?> findList() {
        List<ListItem> items = itemListService.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody ListItem listItem) {
        try{
            int rowsAffected = itemListService.postItem(listItem);
            logger.info("Item inserido com sucesso!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item inserido com sucesso!");
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao inserir o item:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao inserir o item: " + e);
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestBody ListItem listItem) {
        try{
            int rowsAffected = itemListService.deleteItem(listItem);
            logger.info("Item deletado com sucesso!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item deletado com sucesso!");
        }catch(Exception e) {
            logger.error("Ocorreu um erro ao excluir o item:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao excluir o item: " + e);
        }

    }

    @PutMapping("/checkItem")
    public ResponseEntity<?> checkItem(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        Boolean isChecked = (Boolean) requestBody.get("isChecked");

        if (name == null || isChecked == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetros inválidos.");
        }

        try {
            int rowsAffected = itemListService.checkItem(name, isChecked);
            logger.info("Item marcado/desmarcado com sucesso!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item marcado/desmarcado com sucesso!");
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao marcar/desmarcar o item:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao marcar/desmarcar o item: " + e.getMessage());
        }
    }


}
