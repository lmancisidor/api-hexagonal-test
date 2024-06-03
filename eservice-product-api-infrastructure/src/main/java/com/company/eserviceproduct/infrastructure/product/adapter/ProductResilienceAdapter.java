package com.company.eserviceproduct.infrastructure.product.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;
import com.company.eserviceproduct.infrastructure.product.persistence.ProductJpaRepository;
import com.company.eserviceproduct.infrastructure.product.persistence.ProductJpaResilient;

import io.github.resilience4j.retry.annotation.Retry;

@Service("productJpaResilient")
public class ProductResilienceAdapter implements ProductJpaResilient {

	final String UNSTABLE_SERVICE = "UNSTABLE_SERVICE";
	
	private final ProductJpaRepository repository;	

	public ProductResilienceAdapter(ProductJpaRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	@Retry(name = UNSTABLE_SERVICE,fallbackMethod = "defaultProduct")
	public ProductEntity save(ProductEntity productToSave) {
		return repository.save(productToSave);		
	}
	
	private ProductEntity defaultProduct(Exception ex) {
	    return new ProductEntity();
	}

	@Override
	@Retry(name = UNSTABLE_SERVICE,fallbackMethod = "defaultProducts")
	public
	List<ProductEntity> findAll() {
		return repository.findAll();
	}
	
	private List<ProductEntity> defaultProducts(Exception ex) {
	    return new ArrayList<ProductEntity>();
	}

}
