package ru.kata.spring.boot_security.demo.entities;

import lombok.Data;
//import org.springframework.context.annotation.Lazy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
}