package com.example.franchise.domain.api;

import com.example.franchise.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductServicePort {
    Mono<Product> addProduct(Product product);

    Mono<Void> deleteProduct(Long idProduct);

    Mono<Product> updateStock(Long idProduct, Integer stock);

    Mono<Product> getProduct(Long idProduct);

    Flux<Product> getBestByFranchise(Long idFranchise);

    Mono<Product> updateName(Long idProduct, String name);
}
