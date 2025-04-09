package com.todolist;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

@Service
public class ItemListService {

    private static final Logger logger = LogManager.getLogger(ItemListService.class);

    @Autowired
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
