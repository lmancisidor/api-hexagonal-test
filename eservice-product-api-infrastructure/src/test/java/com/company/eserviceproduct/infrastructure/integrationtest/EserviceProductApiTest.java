package com.company.eserviceproduct.infrastructure.integrationtest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.springframework.util.ResourceUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.company.eserviceproduct.infrastructure.EserviceProductApplication;
import com.company.eserviceproduct.infrastructure.product.dto.inbound.ProductRequestToCreate;
import com.company.eserviceproduct.infrastructure.product.dto.outbound.ProductResponse;
import com.company.eserviceproduct.infrastructure.product.entity.ProductEntity;
import com.company.eserviceproduct.infrastructure.product.persistence.ProductJpaRepository;
import com.company.eserviceproduct.infrastructure.product.controller.ProductController;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EserviceProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(ResourceUtils.CLASSPATH_URL_PREFIX + "application.properties")
public class EserviceProductApiTest {

	@Autowired
	ProductController productController;
  
	@Autowired
	ProductJpaRepository productJpaRepository;
	  
	@LocalServerPort
	private Integer port;
	 
	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:16-alpine"
			 //DockerImageName.parse("postgres:15-alpine")
	);

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    productJpaRepository.deleteAll();
  }
  
  @Test
  void shouldSaveCustomer() {
	  ProductRequestToCreate productRequestToCreate1 = new ProductRequestToCreate(11111, "Prod1", "description prod1", true);
	  ProductRequestToCreate productRequestToCreate2 =new ProductRequestToCreate(11110, "Prod2", "description prod2", true);
    
	  productController.create(productRequestToCreate1);
	  productController.create(productRequestToCreate2);

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/products/product")
      .then()
      .statusCode(200)
      .body(".", hasSize(2));
  }  

  @Test
  void shouldGetAllProducts() {
    
	  List<ProductEntity> toSaveProductEntities = List.of(
			  new ProductEntity(null, 11111, "Prod1", "description prod1", true),
			  new ProductEntity(null, 11110, "Prod2", "description prod2", true)
			  );
	  
	 List<ProductEntity> productEntities = productJpaRepository.saveAll(toSaveProductEntities);
	 int sizeDB = productEntities.size(); 
	  
    List<ProductResponse> productResponses = productController.getAll();

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/products")
      .then()
      .statusCode(200)
      .body(".", hasSize(2));
  }
}