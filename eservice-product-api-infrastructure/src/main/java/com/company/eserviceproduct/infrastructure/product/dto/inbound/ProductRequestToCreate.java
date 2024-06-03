package com.company.eserviceproduct.infrastructure.product.dto.inbound;

import org.springframework.validation.annotation.Validated;

import com.company.eserviceproduct.domain.product.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductRequestToCreate(
		@JsonProperty("ean") @NotBlank long ean, 
		@JsonProperty("name") String name, 
		@JsonProperty("description") String description,
		@JsonProperty("isSalable") Boolean isSalable) {
	
    public static Product toDomain(ProductRequestToCreate product) {
        return new Product(null, product.ean, product.name, product.description, product.isSalable);
    }
}