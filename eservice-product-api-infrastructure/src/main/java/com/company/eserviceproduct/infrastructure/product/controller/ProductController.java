package com.company.eserviceproduct.infrastructure.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.eserviceproduct.application.product.port.inbound.ProductServiceInboundPort;
import com.company.eserviceproduct.infrastructure.product.dto.inbound.ProductRequestToCreate;
import com.company.eserviceproduct.infrastructure.product.dto.outbound.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Slf4j
@Tags(value = {
		@Tag(name = "ProductAPI") 
})
public class ProductController {

	
    private ProductServiceInboundPort productServiceInboundPort;
	    
    public ProductController(
    	    @Autowired ProductServiceInboundPort productServiceInboundPort) {
		this.productServiceInboundPort = productServiceInboundPort;
	}


	@PostMapping(path="/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description= "create a product")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "Ok", 
			content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)) }),
	
	})
    public ResponseEntity<ProductResponse> create(@RequestBody(required = true) ProductRequestToCreate productRequestToCreate) {
		log.info("** product before create {}", productRequestToCreate);
        var product = productServiceInboundPort.requestCreate(ProductRequestToCreate.toDomain(productRequestToCreate));
        log.info("** product after create {}",product);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.domainToProductResponse(product));
    }
    

    @GetMapping("/")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Ok", 
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)) }),
    		@ApiResponse(responseCode = "400", description = "Invalid BrandId or ProductId"),
    		@ApiResponse(responseCode = "404", description = "PriceDto not find"),
    		@ApiResponse(responseCode = "500", description = "Internal server error", 
    				content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    			})
    public List<ProductResponse> getAll() {
    	
        return ProductResponse.domainsToProductResponses(productServiceInboundPort.requestFindAll());
    }

}
