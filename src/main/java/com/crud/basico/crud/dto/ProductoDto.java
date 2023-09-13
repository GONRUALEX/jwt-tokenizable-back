package com.crud.basico.crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductoDto {

	@NotBlank
	private String nombre;
	@Min(0)
	private Float precio;
	
	
	public ProductoDto() {
		super();
	}


	public ProductoDto(String nombre, float precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
	
	
	
}
