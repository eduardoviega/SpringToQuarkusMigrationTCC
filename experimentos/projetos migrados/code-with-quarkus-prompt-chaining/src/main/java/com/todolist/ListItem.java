package com.todolist;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "Items")
public class ListItem extends PanacheEntity {

    @Column(columnDefinition = "TEXT", name = "name")
    private String name;

    @Column(columnDefinition = "BOOLEAN", name = "is_checked")
    private boolean isChecked;

    public ListItem() {
    }

    public ListItem(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
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

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import javax.persistence.*;
//import io.quarkus.hibernate.orm.panache.PanacheEntity;
//
//@Entity
//@Table(name = "Items")
//public class ListItem extends PanacheEntity {
//
//    @Column(columnDefinition = "TEXT", name = "name")
//    private String name;
//
//    @Column(columnDefinition = "BOOLEAN", name = "is_checked")
//    private boolean isChecked;
//
//    public ListItem() {
//    }
//
//    public ListItem(String name, boolean isChecked) {
//        this.name = name;
//        this.isChecked = isChecked;
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
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }
//}