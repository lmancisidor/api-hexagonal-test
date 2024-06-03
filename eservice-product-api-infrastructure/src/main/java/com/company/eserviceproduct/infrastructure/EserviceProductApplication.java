package com.company.eserviceproduct.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.company.eserviceproduct.application.config.ConfigScanApplication;
import com.company.eserviceproduct.domain.config.ConfigScanDomain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Eservice-product API", description = "Eservice-product api"))
@ComponentScan(basePackages = {"com.company.eserviceproduct.domain", "com.company.eserviceproduct.application", "com.company.eserviceproduct.infrastructure", "com.company.eserviceproduct"})
@SpringBootApplication(
		scanBasePackageClasses = {ConfigScanDomain.class, ConfigScanApplication.class}
)
public class EserviceProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(EserviceProductApplication.class, args);
	}

}