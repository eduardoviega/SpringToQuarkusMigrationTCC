package com.todolist;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ItemListRepository {

    @Inject
    EntityManager em;

    public List<ListItem> findItems() {
        return em.createQuery("SELECT i FROM ListItem i ORDER BY i.id", ListItem.class).getResultList();
    }

    @Transactional
    public int postItem(String name) {
        String sql = "INSERT INTO ListItem (name, isChecked) VALUES (:name, false)";
        return em.createQuery(sql).setParameter("name", name).executeUpdate();
    }

    @Transactional
    public int deleteItem(String name) {
        String sql = "DELETE FROM ListItem WHERE name = :name";
        return em.createQuery(sql).setParameter("name", name).executeUpdate();
    }

    @Transactional
    public int checkItem(String name, boolean isChecked) {
        String sql = "UPDATE ListItem SET isChecked = :isChecked WHERE name = :name";
        return em.createQuery(sql).setParameter("isChecked", isChecked).setParameter("name", name).executeUpdate();
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//
//import javax.sql.DataSource;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class ItemListRepository {
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Inject
//    public ItemListRepository(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    public List<ListItem> findItems() {
//        return jdbcTemplate.queryForList("SELECT * FROM ITEMS ORDER BY id");
//    }
//
//    public int postItem(String name) {
//        String sql = "INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)";
//        return jdbcTemplate.update(sql, name, false);
//    }
//
//    public int deleteItem(String name) {
//        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
//        return jdbcTemplate.update(sql, name);
//    }
//
//    public int checkItem(String name, boolean isChecked) {
//        String sql = "UPDATE ITEMS SET is_checked=? WHERE name = ?";
//        return jdbcTemplate.update(sql, isChecked, name);
//    }
//}