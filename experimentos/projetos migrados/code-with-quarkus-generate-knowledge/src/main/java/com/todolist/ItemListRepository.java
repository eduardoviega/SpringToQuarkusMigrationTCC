package com.todolist;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ItemListRepository {

    @Inject
    EntityManager entityManager;

    public List<ListItem> findItems() {
        return entityManager.createQuery("SELECT i FROM ListItem i ORDER BY i.id", ListItem.class).getResultList();
    }

    @Transactional
    public int postItem(String name) {
        ListItem item = new ListItem();
        item.setName(name);
        item.setChecked(false);
        entityManager.persist(item);
        return 1; // Assuming the persistence always succeeds
    }

    @Transactional
    public int deleteItem(String name) {
        return entityManager.createQuery("DELETE FROM ListItem WHERE name = :name").setParameter("name", name).executeUpdate();
    }

    @Transactional
    public int checkItem(String name, boolean isChecked) {
        return entityManager.createQuery("UPDATE ListItem SET isChecked = :isChecked WHERE name = :name")
                .setParameter("isChecked", isChecked)
                .setParameter("name", name)
                .executeUpdate();
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class ItemListRepository {
//
//    @Inject
//    EntityManager entityManager;
//
//    public List<ListItem> findItems() {
//        return entityManager.createQuery("SELECT i FROM ListItem i ORDER BY i.id", Map.class).getResultList();
//    }
//
//    public int postItem(String name) {
//        ListItem item = new ListItem();
//        item.setName(name);
//        item.setChecked(false);
//        entityManager.persist(item);
//        return 1; // Assuming the persistence always succeeds
//    }
//
//    public int deleteItem(String name) {
//        return entityManager.createQuery("DELETE FROM ListItem WHERE name = :name").setParameter("name", name).executeUpdate();
//    }
//
//    public int checkItem(String name, boolean isChecked) {
//        return entityManager.createQuery("UPDATE ListItem SET isChecked = :isChecked WHERE name = :name")
//                .setParameter("isChecked", isChecked)
//                .setParameter("name", name)
//                .executeUpdate();
//    }
//}