package com.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class ItemListRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemListRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Map<String, Object>> findItems() {
        return jdbcTemplate.queryForList("SELECT * FROM ITEMS ORDER BY id");
    }

    public int postItem(String name) {
        String sql = "INSERT INTO ITEMS (name, is_checked) VALUES (?, ?)";
        return jdbcTemplate.update(sql, name, false);
    }


    public int deleteItem(String name) {
        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
        return jdbcTemplate.update(sql, name);
    }

    public int checkItem(String name, boolean isChecked) {
        String sql = "UPDATE ITEMS SET is_checked=? WHERE name = ?";
        return jdbcTemplate.update(sql, isChecked, name);
    }
}
