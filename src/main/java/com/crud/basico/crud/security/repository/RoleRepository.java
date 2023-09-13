package com.crud.basico.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.basico.crud.security.entity.Role;
import com.crud.basico.crud.security.enums.RoleNombre;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	 Optional<Role> findByRoleNombre(RoleNombre roleNombre);

}
