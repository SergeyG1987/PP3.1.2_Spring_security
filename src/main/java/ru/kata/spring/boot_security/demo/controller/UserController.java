package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.security.Principal;
//import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    // DI через конструктор для работы с пользователями
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Обработка GET-запроса по адресу /user
     * Отображает список всех пользователей
     */
    @GetMapping("/user") // Обработка запросов, начинающихся с /users
    public String showUsers(Model model, Principal principal) {
        // Получаем пользователей из базы данных
        User user = userService.getUserByName(principal.getName());
        // Передаем пользователей в модель для отображения в шаблоне
        model.addAttribute("user", user);
        // Возвращаем имя шаблона users.html
        return "user";
    }
}