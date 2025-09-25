package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> findAll() { return roleRepository.findAll(); }
    public Role findByName(String name) { return roleRepository.findByName(name).orElse(null); }
    public void save(Role role) { roleRepository.save(role); }
}
