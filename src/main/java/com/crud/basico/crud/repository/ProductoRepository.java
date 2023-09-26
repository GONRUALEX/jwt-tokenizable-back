package com.crud.basico.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.basico.crud.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	Optional<Producto> findByName(String nombre);
	boolean existsByName(String nombre);

}
