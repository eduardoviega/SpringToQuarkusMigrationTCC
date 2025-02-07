package com.todolist;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ItemListRepository {
    @Inject
    DataSource dataSource;

    @Inject
    EntityManager em;

    public List<ListItem> findItems() {
        return em.createQuery("SELECT i FROM ListItem i ORDER BY i.id", ListItem.class).getResultList();
    }

    @Transactional
    public int postItem(String name) throws SQLException {
        var preparedStatement = dataSource.getConnection().prepareStatement("INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setBoolean(2, false);

        return preparedStatement.executeUpdate();
    }

    @Transactional
    public int deleteItem(String name) throws SQLException {
        var preparedStatement = dataSource.getConnection().prepareStatement("DELETE FROM ITEMS WHERE NAME = ?");
        preparedStatement.setString(1, name);

        return preparedStatement.executeUpdate();
    }

    @Transactional
    public int checkItem(String name, boolean isChecked) throws SQLException {
        var preparedStatement = dataSource.getConnection().prepareStatement("UPDATE ITEMS SET is_checked=? WHERE name = ?");
        preparedStatement.setBoolean(1, isChecked);
        preparedStatement.setString(2, name);

        return preparedStatement.executeUpdate();
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
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class ItemListRepository {
//    @Inject
//    DataSource dataSource;
//
//    private io.agroal.api.AgroalDataSource jdbcTemplate;
//
//    public List<ListItem> findItems() {
//        // replace this method with an appropriate implementation using a native query
//        // or a PanacheEntity derived query
//        return null;
//    }
//
//    public int postItem(String name) {
//        return jdbcTemplate.getConnection().prepareStatement("INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)").executeUpdate();
//    }
//
//    public int deleteItem(String name) {
//        return jdbcTemplate.getConnection().prepareStatement("DELETE FROM ITEMS WHERE NAME = ?").executeUpdate();
//    }
//
//    public int checkItem(String name, boolean isChecked) {
//        return jdbcTemplate.getConnection().prepareStatement("UPDATE ITEMS SET is_checked=? WHERE name = ?").executeUpdate();
//    }
//}