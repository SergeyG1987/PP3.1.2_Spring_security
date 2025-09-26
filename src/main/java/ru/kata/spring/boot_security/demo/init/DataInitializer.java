package ru.kata.spring.boot_security.demo.init;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;  // Теперь интерфейс!

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        if (roleService.findByName("ROLE_ADMIN") == null) {
            roleService.save(adminRole);
        }
        if (roleService.findByName("ROLE_USER") == null) {
            roleService.save(userRole);
        }

        adminRole = roleService.findByName("ROLE_ADMIN");
        userRole = roleService.findByName("ROLE_USER");

        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("user@example.com").isEmpty()) {
            User user = new User();
            user.setName("User");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }
}