package ru.kata.spring.boot_security.demo.model;


//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import lombok.Data;

@Entity
@Data
//геттеры, сеттеры, хэшкод и equals созданы ломбок
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min=3, max = 99, message = "Имя пользователя не менее 3 и не более 99 знаков")
    private String username;
    //@Size(min=8, message = "Пароль не менее 8 знаков")
    private String password;
    private String firstname;
    private String lastname;
    @Email
    private String email;

    @ManyToMany//(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    // getPassword и getUsername в ломбок

    public User() {
    }

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

}
