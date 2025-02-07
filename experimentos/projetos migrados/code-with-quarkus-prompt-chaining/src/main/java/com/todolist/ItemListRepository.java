package com.todolist;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ItemListRepository implements PanacheRepository<ListItem> {

    public List<ListItem> findItems() {
        return getEntityManager().createNativeQuery("SELECT * FROM ITEMS ORDER BY id", ListItem.class).getResultList();
    }

    @Transactional
    public int postItem(String name) {
        String sql = "INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)";
        return getEntityManager().createNativeQuery(sql)
                .setParameter(1, name)
                .setParameter(2, false)
                .executeUpdate();
    }

    @Transactional
    public int deleteItem(String name) {
        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
        return getEntityManager().createNativeQuery(sql)
                .setParameter(1, name)
                .executeUpdate();
    }

    @Transactional
    public int checkItem(String name, boolean isChecked) {
        String sql = "UPDATE ITEMS SET is_checked=? WHERE name = ?";
        return getEntityManager().createNativeQuery(sql)
                .setParameter(1, isChecked)
                .setParameter(2, name)
                .executeUpdate();
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import javax.enterprise.context.ApplicationScoped;
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class ItemListRepository implements PanacheRepository<ListItem> {
//
//    public List<ListItem> findItems() {
//        return getEntityManager().createNativeQuery("SELECT * FROM ITEMS ORDER BY id", Map.class).getResultList();
//    }
//
//    public int postItem(String name) {
//        String sql = "INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)";
//        return getEntityManager().createNativeQuery(sql)
//                .setParameter(1, name)
//                .setParameter(2, false)
//                .executeUpdate();
//    }
//
//    public int deleteItem(String name) {
//        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
//        return getEntityManager().createNativeQuery(sql)
//                .setParameter(1, name)
//                .executeUpdate();
//    }
//
//    public int checkItem(String name, boolean isChecked) {
//        String sql = "UPDATE ITEMS SET is_checked=? WHERE name = ?";
//        return getEntityManager().createNativeQuery(sql)
//                .setParameter(1, isChecked)
//                .setParameter(2, name)
//                .executeUpdate();
//    }
//}