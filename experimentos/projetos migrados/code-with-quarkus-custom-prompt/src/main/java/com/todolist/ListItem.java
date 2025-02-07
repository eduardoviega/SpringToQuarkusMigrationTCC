package com.todolist;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}


// ANTES DE REALIZAR QUALQUER ALTERAÇÃO MANUAL:

//package com.todolist;
//
//import jakarta.persistence.*;
//        import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "Items")
//public class ListItem {
//
//    @Id
//    @jakarta.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(columnDefinition = "TEXT", name = "name")
//    private String name;
//
//    @Column(columnDefinition = "BOOLEAN", name = "is_checked")
//    private boolean isChecked;
//
//}