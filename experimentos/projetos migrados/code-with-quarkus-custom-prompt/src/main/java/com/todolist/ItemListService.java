package com.todolist;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ItemListService {

    private static final Logger logger = Logger.getLogger(ItemListService.class);

    @Inject
    ItemListRepository itemListRepository;

    public List<ListItem> findAll() {
        return itemListRepository.findItems();
    }

    public int postItem(ListItem listItem) {
        return itemListRepository.postItem(listItem.getName());
    }


    public int deleteItem(ListItem listItem) {
        return itemListRepository.deleteItem(listItem.getName());
    }

    public int checkItem(String name, Boolean isChecked) {
        logger.info(isChecked);
        return itemListRepository.checkItem(name, isChecked);
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import org.jboss.logging.Logger;
//
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class ItemListService {
//
//    private static final Logger logger = Logger.getLogger(ItemListService.class);
//
//    @Inject
//    ItemListRepository itemListRepository;
//
//    public List<ListItem> findAll() {
//        return itemListRepository.findItems();
//    }
//
//    public int postItem(ListItem listItem) {
//        return itemListRepository.postItem(listItem.getName());
//    }
//
//
//    public int deleteItem(ListItem listItem) {
//        return itemListRepository.deleteItem(listItem.getName());
//    }
//
//    public int checkItem(String name, Boolean isChecked) {
//        logger.info(isChecked);
//        return itemListRepository.checkItem(name, isChecked);
//    }
//}