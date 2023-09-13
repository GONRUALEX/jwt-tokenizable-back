package com.crud.basico.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.basico.crud.security.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
	Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
	Optional<Usuario> findByTokenPassword(String tokenPassword);
	boolean existsByNombreUsuario(String nombreUsuario);
	boolean existsByEmail(String email);
	
}
