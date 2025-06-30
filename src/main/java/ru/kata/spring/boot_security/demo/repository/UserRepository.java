package ru.kata.spring.boot_security.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Optional;

//для выполнения стандартных запросов к БД
public interface UserRepository extends JpaRepository<User, Long> {
    //@Override
    Optional<User> findByUsername(String username);
}