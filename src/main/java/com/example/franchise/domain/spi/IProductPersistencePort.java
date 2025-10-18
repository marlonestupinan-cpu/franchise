package com.example.franchise.domain.spi;

import com.example.franchise.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> saveProduct(Product product);

    Mono<Void> deleteProduct(Long idProduct);

    Mono<Product> getProduct(Long idProduct);

    Flux<Product> getBestProductFromFranchise(Long idFranchise);
}
