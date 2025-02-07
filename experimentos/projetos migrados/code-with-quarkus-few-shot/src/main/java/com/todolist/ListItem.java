package com.todolist;

import jakarta.persistence.*;

@Entity
@Table(name = "Items")
public class ListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", name = "name")
    private String name;

    @Column(columnDefinition = "BOOLEAN", name = "is_checked")
    private boolean isChecked;

    public ListItem() {}

    public ListItem(Long id, String name, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Items")
//public class ListItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(columnDefinition = "TEXT", name = "name")
//    private String name;
//
//    @Column(columnDefinition = "BOOLEAN", name = "is_checked")
//    private boolean isChecked;
//
//    public ListItem() {}
//
//    public ListItem(Long id, String name, boolean isChecked) {
//        this.id = id;
//        this.name = name;
//        this.isChecked = isChecked;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isChecked() {
//        return isChecked;
//    }
//
//    public void setChecked(boolean isChecked) {
//        this.isChecked = isChecked;
//    }
//}