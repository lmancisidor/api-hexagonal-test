package com.company.eserviceproduct.application.product.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.eserviceproduct.application.product.port.inbound.ProductServiceInboundPort;
import com.company.eserviceproduct.application.product.port.outbound.ProductServiceOutboundPort;
import com.company.eserviceproduct.domain.product.model.Product;

//*****************************************
//** Orquestacion de la logica de negocio**
//*****************************************

// Implementacion del adapter en base a nuestro puerto primario (puerto de tipo punto de entrada de una peticion)
@Service("productServiceInboundPort")
public class ProductServiceInboundAdapter implements ProductServiceInboundPort {

	// Para resolver las peticiones usamos una referencia al puerto secundario (puerto punto de comunicacion de nuestro sistema con componente secundario: bbdd, apis, ...) 
	
	private ProductServiceOutboundPort productServiceOutboundPort;
    
	public ProductServiceInboundAdapter(@Autowired ProductServiceOutboundPort portOutbound) {
		this.productServiceOutboundPort = portOutbound;
	}

	@Override
	public Product requestCreate(Product productInbound) {
		return productServiceOutboundPort.create(productInbound);
	}

	@Override
	public List<Product> requestFindAll() {
		return productServiceOutboundPort.findAll();
	}

}
