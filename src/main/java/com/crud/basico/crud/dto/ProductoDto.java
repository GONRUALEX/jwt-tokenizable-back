package com.crud.basico.crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductoDto {

	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String imageUrl;
	@Min(0)
	private String price;
	
	
	public ProductoDto() {
		super();
	}
	public ProductoDto(@NotBlank String name, @NotBlank String description, @NotBlank String imageUrl,
			@Min(0) String price) {
		super();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}


	
}
