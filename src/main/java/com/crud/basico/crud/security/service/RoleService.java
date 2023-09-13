package com.crud.basico.crud.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.basico.crud.security.entity.Role;
import com.crud.basico.crud.security.enums.RoleNombre;
import com.crud.basico.crud.security.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService{

@Autowired
RoleRepository roleRepository;

public Optional<Role> getByRoleNombre(RoleNombre roleNombre){
	return roleRepository.findByRoleNombre(roleNombre);
}

public void save (Role role) {
	roleRepository.save(role);
}

}
