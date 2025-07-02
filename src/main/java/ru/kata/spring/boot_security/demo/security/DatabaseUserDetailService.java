package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // или ваш репозиторий

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получение Optional<User>
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Проверка наличия пользователя
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        // Преобразование ролей в GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        // Создаем CustomUserDetails с пользователем и его авторитетами
        return new CustomUserDetails(user, authorities);
    }

    // Универсальный метод преобразования ролей в GrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}