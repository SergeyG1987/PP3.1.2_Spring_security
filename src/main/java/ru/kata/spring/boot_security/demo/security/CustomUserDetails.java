package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;

/** * Класс CustomUserDetails реализует интерфейс UserDetails, представляющий информацию о пользователе, * используемую Spring Security для аутентификации и авторизации. */
public class CustomUserDetails implements UserDetails {

    /** * Хранит ссылку на сущность пользователя, содержащую основную информацию о пользователе. */
    private final User user;

    /** * Конструктор принимает объект User и сохраняет его в приватное поле. * * @param user объект сущности User, полученный из базы данных. */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /** * Возвращает коллекцию ролевых разрешений пользователя (GrantedAuthority), * которые определяют доступные ресурсы и операции для данного пользователя. * * @return Коллекция GrantedAuthority. */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getAuthorities(); // Возвращаем роли пользователя, полученные из сущности User.
    }

    /** * Возвращает пароль пользователя, который был введен при регистрации. * * @return Пароль пользователя. */
    @Override
    public String getPassword() {
        return user.getPassword(); // Возвращаем пароль пользователя из сущности User.
    }

    /** * Возвращает уникальное имя пользователя (логин), используемое для идентификации пользователя. * * @return Имя пользователя. */
    @Override
    public String getUsername() {
        return user.getUsername(); // Возвращаем имя пользователя из сущности User.
    }

    /** * Проверяет, истек ли срок действия учетной записи пользователя. * В данном случае всегда возвращает true, предполагая, что учетная запись действительна. * * @return Всегда true. */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Учетная запись не просрочена.
    }

    /** * Проверяет, заблокирована ли учетная запись пользователя. * В данном случае всегда возвращает true, предполагая, что учетная запись разблокирована. * * @return Всегда true. */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Учетная запись не заблокирована.
    }

    /** * Проверяет, истёк ли срок действия пароля пользователя. * В данном случае всегда возвращает true, предполагая, что пароль действителен. * * @return Всегда true. */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Срок действия пароля не истек.
    }

    /** * Проверяет, включена ли учетная запись пользователя. * В данном случае всегда возвращает true, предполагая, что учетная запись активна. * * @return Всегда true. */
    @Override
    public boolean isEnabled() {
        return true; // Учетная запись включена.
    }
}
