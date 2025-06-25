package ru.kata.spring.boot_security.demo.init;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void initializedDataBase() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);
        Set<Role> adminRoleSet = new HashSet<>();
        Set<Role> userRoleSet = new HashSet<>();
        System.out.println(adminRoleSet.add(roleAdmin));
        System.out.println(adminRoleSet.add(roleUser));
        System.out.println(userRoleSet.add(roleUser));
        User admin = new User(
                "admin", "admin", "Администратор", "Администратор", "admin@kata.ru");
        User admin2 = new User("localadmin", "localadmin", "Локальный админ",
                "Локальный админ", "ladmin@kata.ru");
        User user1 =
                new User("user", "user", "Пользователь", "Пользователь", "user@kata.ru");
        User user2 = new User(
                "Пользователь", "Пользователь", "Пользователь", "Пользователь", "user123@kata.ru");
        admin.setRoles(adminRoleSet);
        admin2.setRoles(adminRoleSet);
        user1.setRoles(userRoleSet);
        user2.setRoles(userRoleSet);
        userService.addUser(admin);
        userService.addUser(admin2);
        userService.addUser(user1);
        userService.addUser(user2);
    }
}
