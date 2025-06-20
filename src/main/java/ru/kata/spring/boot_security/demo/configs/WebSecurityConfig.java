package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .authorizeRequests()
                // Требовать авторизацию для главной страницы
                .antMatchers("{/}", "/index").authenticated()
                // остальные правила
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .permitAll();
    }

    // аутентификация inMemory
    @Bean       //метод возвращает компонент, который должен быть управляемым контейнером Spring
    @Override   //переопределение реализации сервиса пользователей в Spring Security
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()// создание пользователя с логином/паролем/ролью user
                //User.withDefaultPasswordEncoder()
                .username("user")
                //.password("{bcrypt}$2a$12$rIxFRYcu1jlD2tpXv7oODO.5c9WwTetznHimgmQJTOg/5Af.8xlv6") //user
                .password("{noop}user")
                .roles("USER")
                .build();   //создает финальный объект UserDetails, который затем передается в InMemoryUserDetailsManager.

        UserDetails admin = User.builder()// создание административного пользователя admin
                //User.withDefaultPasswordEncoder()
                .username("admin")
                //.password("{bcrypt}$2a$12$CE.5kDEza.xAwrlxJRtnReY4kwQK2BWRTuyADXqHqxQ3Oi9gMLvGm") //admin
                .password("{noop}admin")
                .roles("ADMIN", "USER")
                .build();   //создает финальный объект UserDetails, который затем передается в InMemoryUserDetailsManager.

        return new InMemoryUserDetailsManager(user, admin);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}