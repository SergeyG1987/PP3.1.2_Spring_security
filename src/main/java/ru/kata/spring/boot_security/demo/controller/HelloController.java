package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// Объявляем класс как контроллер Spring
@Controller
public class HelloController {

	// Обработка GET-запроса по URL "/"
	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		return "index";
	}
}