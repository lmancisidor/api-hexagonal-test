package com.company.eserviceproduct.infrastructure.product.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.company.eserviceproduct.domain.product.model.Product;
import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductMapper {
	
	static ModelMapper modelMapper = new ModelMapper();
    
	private ProductMapper() {
    	
    }
    
	public static ProductEntity domainToEntity(Product product){
		log.info("== domainToEntity product with values: {}", product);
		ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
		log.info("== domainToEntity product with values: {}  convert to Entity with values: {}", product, productEntity);
		return productEntity;
	}
	
	public static Product entityToDomain(ProductEntity productEntity){
	    return modelMapper.map(productEntity, Product.class);
	}

	public static List<Product> entitiesToDomains(List<ProductEntity> productEntities){
		List<Product> products = new ArrayList<>();		
		products = productEntities.stream().map(ProductMapper::entityToDomain).toList();
	    return products;
	}
	
}
