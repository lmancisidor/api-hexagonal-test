package com.company.eserviceproduct.infrastructure.product.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.eserviceproduct.application.product.port.outbound.ProductServiceOutboundPort;
import com.company.eserviceproduct.domain.product.model.Product;
import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;
import com.company.eserviceproduct.infrastructure.product.mapper.ProductMapper;
import com.company.eserviceproduct.infrastructure.product.persistence.ProductJpaResilient;

@Service("productServiceOutboundPort")
public class ProductJpaAdapter implements ProductServiceOutboundPort {
    
    private final ProductJpaResilient productJpaResilient;
    
    public ProductJpaAdapter(@Autowired ProductJpaResilient resilience) {
        this.productJpaResilient = resilience;
    }

    @Override
    public Product create(Product product) {
    	ProductEntity productEntityToSave = ProductMapper.domainToEntity(product);    	
    	ProductEntity productEntitySaved = productJpaResilient.save(productEntityToSave);		
    	return ProductMapper.entityToDomain(productEntitySaved);		
    }

    @Override
    public List<Product> findAll() {
        return ProductMapper.entitiesToDomains(productJpaResilient.findAll());
    }
}
