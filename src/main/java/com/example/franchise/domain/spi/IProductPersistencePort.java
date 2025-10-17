package com.example.franchise.domain.spi;

import com.example.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> createProduct(Product product);
}
