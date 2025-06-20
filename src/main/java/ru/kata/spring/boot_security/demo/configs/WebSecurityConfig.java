package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                            // Начало конфигурации правил авторизации в Spring Security
                .antMatchers("/", "/index").permitAll()     // Позволяет всем заходить на / и /index без авторизации
                .antMatchers("/admin/**").hasRole("ADMIN")    // Доступ для роли ADMIN к /admin/**
                .antMatchers("/user").hasRole("USER")         // Доступ для роли USER к /user
                .anyRequest().authenticated()                   // все остальные требуют входа в систему
                .and()                                          // завершение текущего блока настроек и перехода к следующему разделу конфигурации
                .formLogin().successHandler(successUserHandler) // Использует форму входа, при этом после успешной авторизации вызывается successUserHandler.
                .permitAll()                                    // разрешить доступ к форме логина всем
                .and()
                .logout().logoutSuccessUrl("/")
                .permitAll();                                   // разрешить выход из системы всем
    }

    // аутентификация inMemory
    @Bean       //метод возвращает компонент, который должен быть управляемым контейнером Spring
    @Override   //переопределение реализации сервиса пользователей в Spring Security
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()// создание пользователя с логином/паролем/ролью user
                //User.withDefaultPasswordEncoder()
                .username("user")
                .password("{bcrypt}$2a$12$rIxFRYcu1jlD2tpXv7oODO.5c9WwTetznHimgmQJTOg/5Af.8xlv6") //user
                .roles("USER")
                .build();   //создает финальный объект UserDetails, который затем передается в InMemoryUserDetailsManager.

        UserDetails admin = User.builder()// создание административного пользователя admin
                //User.withDefaultPasswordEncoder()
                .username("admin")
                .password("{bcrypt}$2a$12$CE.5kDEza.xAwrlxJRtnReY4kwQK2BWRTuyADXqHqxQ3Oi9gMLvGm") //admin
                .roles("ADMIN", "USER")
                .build();   //создает финальный объект UserDetails, который затем передается в InMemoryUserDetailsManager.

        return new InMemoryUserDetailsManager(user, admin);
    }
}