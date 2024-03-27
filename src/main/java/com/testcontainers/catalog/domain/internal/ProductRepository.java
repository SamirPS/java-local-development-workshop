package com.testcontainers.catalog.domain.internal;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

interface ProductRepository extends MongoRepository<ProductEntity, String> {
    Optional<ProductEntity> findByCode(String code);

    @Query("db.products.updateOne({\"code\": \":code\"}, {$set: {\"image\": \":image\"}})")
    void updateProductImage(@Param("code") String code, @Param("image") String image);
}
