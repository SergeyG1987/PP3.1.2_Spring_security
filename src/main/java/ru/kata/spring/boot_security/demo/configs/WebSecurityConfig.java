package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ru.kata.spring.boot_security.demo.security.DatabaseUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private DatabaseUserDetailService databaseUserDetailService;

    //конструктор для инициализации бина, который вызывает обработчик успешной авторизации
    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void setCustomUserDetailService(DatabaseUserDetailService databaseUserDetailService) {
        this.databaseUserDetailService = databaseUserDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                            // Конфигурация политик авторизации запросов
                .antMatchers("/", "/index").permitAll()     //главная страница доступна всем
                .antMatchers("/admin/**").hasRole("ADMIN")    //страница админа доступна роли АДМИН
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER") //страница юзера доступна ролям АДМИН и ЮЗЕР
                .anyRequest().authenticated()// все остальные запросы требуют авторизации
                .and()
                .formLogin().successHandler(successUserHandler) //форма авторизации использует successUserHandler
                .permitAll()                                    // форма авторизации доступна всем
                .and()
                .logout().logoutSuccessUrl("/login")//при выходе вернуть на страницу авторизации
                .permitAll(); //доступно всем
    }

    //настройка шифрования bcrypt пароля для хранения в БД
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //конфигурирование провайдера аутентификации
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(databaseUserDetailService); //Проверка наличия УЗ (по имени) в БД и загрузка о нём инфо
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); //Сравнивает введённые и хранимые пароли, применяя шифрование
        return authenticationProvider; //возврат результата аутентификации (прошёл/ не прошёл)
    }


    @Bean
    public SpringTemplateEngine templateEngine(
            ITemplateResolver templateResolver) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine(); //компиляция HTML-шаблонов с использованием Thymeleaf и рендеринг страниц на стороне сервера
        templateEngine.setTemplateResolver(templateResolver); //резолвер помогает определить локализацию html-Шаблонов
        templateEngine.addDialect(
                new SpringSecurityDialect()); // обеспечивает интеграцию Thymeleaf с Spring Security
        // для контроля доступа и отображения элементов страницы в зависимости от текущего статуса авторизованного пользователя
        return templateEngine;
    }
}