package com.company.eserviceproduct.application.product.port.inbound;

import java.util.List;

import com.company.eserviceproduct.domain.product.model.Product;

public interface ProductServiceInboundPort {
	
	Product requestCreate(Product product);
	List<Product> requestFindAll();
}