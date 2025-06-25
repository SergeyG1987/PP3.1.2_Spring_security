package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;


import java.util.List;

@Service
@Transactional
public class RoleServiceImpl {
    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getListRoles(){
        return roleRepository.findAll(Sort.by("name"));
    }
    @Transactional
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

}
