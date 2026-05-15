package com.roypr.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //spin up the springboot application on a random port for testing
class ProductServiceApplicationTests {

	@ServiceConnection //indicates to Springboot that you are using a MongoDB TestContainer
	// and automatically sets the spring.data.mongodb.uri property dynamically
	// according to host and port assigned to Mongodb test container.
	//Instead of the prod db, use a different test mongo container to run tests against
	// a real MongoDB instance in a containerized environment
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort //inject the random port assigned to the Spring boot application into the field during testing dynamically,
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static{
	 mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
		{
		"name":"iPhone 14 Pro",
		"description":"test",
		"price":1000
		}
		""";
		RestAssured.given()
				.header("Content-Type", "application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 14 Pro"))
				.body("price", Matchers.equalTo(1000));
	}



}
