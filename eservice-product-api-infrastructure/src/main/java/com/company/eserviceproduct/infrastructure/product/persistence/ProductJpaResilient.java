package com.company.eserviceproduct.infrastructure.product.persistence;

import java.util.List;

import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;

public interface ProductJpaResilient {
	
	ProductEntity save(ProductEntity productEntity);
	List<ProductEntity> findAll();

}
