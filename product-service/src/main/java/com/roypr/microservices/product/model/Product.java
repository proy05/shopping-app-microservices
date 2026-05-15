package com.roypr.microservices.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value="product") //map the class to Mongo db collection named "product"
@AllArgsConstructor //generate a constructor with all the fields as parameters
@NoArgsConstructor //generate a constructor with no parameters
@Builder //generate a builder pattern for the class, allowing you to create instances of Product
@Data //generate getters, setters, toString, equals, and hashCode methods for the class
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;


}
