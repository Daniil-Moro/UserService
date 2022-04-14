package net.proselyte.springbootdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;
    @Column(name = "name")
    private String name;

//    public Role(String name) {
//        this.name = name;
//    }
//
//    public Role() {
//
//    }
}
