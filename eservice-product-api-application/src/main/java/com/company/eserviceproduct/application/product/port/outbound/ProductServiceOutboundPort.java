package com.company.eserviceproduct.application.product.port.outbound;

import java.util.List;

import com.company.eserviceproduct.domain.product.model.Product;

public interface ProductServiceOutboundPort {
	
	Product create(Product product);
	List<Product> findAll();

}
