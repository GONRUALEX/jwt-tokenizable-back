package com.crud.basico.crud.security.entity;

import com.crud.basico.crud.security.enums.RoleNombre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


@Entity
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private RoleNombre roleNombre;

	public Role() {
		super();
	}

	public Role( @NotNull RoleNombre roleNombre) {
		super();
		this.roleNombre = roleNombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleNombre getRoleNombre() {
		return roleNombre;
	}

	public void setRoleNombre(RoleNombre roleNombre) {
		this.roleNombre = roleNombre;
	}
	
	
	
	
}
