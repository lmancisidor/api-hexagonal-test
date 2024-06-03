package com.company.eserviceproduct.domain.product.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Validated
public class Product{
		
	@JsonProperty("id") 
	Long id; 
	
	@JsonProperty("ean") 
	@NotBlank 
	long ean;
	
	@JsonProperty("name") 
	String name;
	
	@JsonProperty("description") 
	String description;
	
	@JsonProperty("isSalable") 
	Boolean isSalable;
}
