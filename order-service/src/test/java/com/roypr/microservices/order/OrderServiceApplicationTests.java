package com.roypr.microservices.order;

import com.roypr.microservices.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //spin up the springboot application on a random port for testing
@AutoConfigureWireMock(port=0) //use a random port for Wiremock server for mocking Inventory service
class OrderServiceApplicationTests {

	@ServiceConnection//indicates to Springboot that you are using a MySQL TestContainer and automatically sets the spring.datasource.url property dynamically according to host and port assigned to mysql test container
	//Instead of the prod db, use a different test MySQL container to run tests against a real mysql instance in a containerized environment
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort //inject the random port assigned to the Spring boot application into the field during testing dynamically,
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder() {
		String skuCode = "iphone_15";
		int quantity = 10;
		String requestBody = """
                {
                     "skuCode": "%s",
                     "price": 1000,
                     "quantity": %d
                }
                """.formatted(skuCode, quantity);

		InventoryClientStub.stubInventoryCall(skuCode, quantity); //call Wiremock stub for Inventory service

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("Order placed successfully"));
	}
}