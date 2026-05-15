package com.roypr.microservices.product.repository;

import com.roypr.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository interface for the Product entity.
It extends MongoRepository, which provides CRUD operations and query methods for MongoDB.
The first generic parameter is the type of the entity (Product), and the second is the type of the entity's ID (String). */
public interface ProductRepository extends MongoRepository<Product,String> {
}
