package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
//import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    // Внедрение сервиса через конструктор для работы с пользователями
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Обработка GET-запроса по адресу /user
     * Отображает список всех пользователей
     */
    @GetMapping("/user") // Обработка запросов, начинающихся с /users
    public String listUsers(Model model, Principal principal) {
        // Получаем пользователей из базы данных
        User user = userService.getUserByName(principal.getName());
        // Передаем пользователей в модель для отображения в шаблоне
        model.addAttribute("user", user);
        // Возвращаем имя шаблона (например, users.html)
        return "user";
    }
}