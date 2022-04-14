package net.proselyte.springbootdemo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import net.proselyte.springbootdemo.serializer.UserSerializer;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "users")
@JsonSerialize(using = UserSerializer.class)
public class User {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private @Id  Long id;
    @Column(name = "username")
    @Id
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

//    public String getPassword() {
//        return password;
//    }

//    public User(String username, String password, Role role) {
//        this.password = password;
//        this.role = role;
//        this.username = username;
//    }
//
//    public User() {
//
//    }

    public String getRole(){
        return role.getName();
    }


}
