package com.company.eserviceproduct.infrastructure.product.dto.outbound;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.company.eserviceproduct.domain.product.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponse (
		@JsonProperty("id") Long id, 
		@JsonProperty("ean") @NotBlank long ean, 
		@JsonProperty("name") String name, 
		@JsonProperty("description") String description,
		@JsonProperty("isSalable") Boolean isSalable) {
	
    public static ProductResponse domainToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getEan(), product.getName(), product.getDescription(), product.getIsSalable());
    }
    
    public static List<ProductResponse> domainsToProductResponses(List<Product> products) {
        return products.stream().map(ProductResponse::domainToProductResponse).toList();
    }
}