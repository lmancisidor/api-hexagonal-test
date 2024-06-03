package com.company.eserviceproduct.infrastructure.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;
public interface ProductJpaRepository  extends JpaRepository<ProductEntity, Long> {
	
	//@Retry(name = "", fallbackMethod = "") 
	//<S extends JpaRepository<ProductEntity, Long>> S save(S entity);
	
	
}
