package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IProductServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final IBranchServicePort branchServicePort;

    @Override
    public Mono<Product> addProduct(Product product) {
        return branchServicePort
                .getBranch(product.getBranch().getId())
                .flatMap(branch -> productPersistencePort.saveProduct(product));
    }

    @Override
    public Mono<Void> deleteProduct(Long idProduct) {
        return productPersistencePort.deleteProduct(idProduct);
    }

    @Override
    public Mono<Product> updateStock(Long idProduct, Integer stock) {
        return getProduct(idProduct)
                .map(product -> {
                    product.setStock(stock);
                    return product;
                })
                .flatMap(productPersistencePort::saveProduct);
    }

    @Override
    public Mono<Product> getProduct(Long idProduct) {
        return productPersistencePort
                .getProduct(idProduct)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_FOUND, idProduct.toString())));
    }

    @Override
    public Flux<Product> getBestByFranchise(Long idFranchise) {
        return productPersistencePort
                .getBestProductFromFranchise(idFranchise)
                .flatMap(product -> branchServicePort
                        .getBranch(product.getBranch().getId())
                        .map(branch -> {
                            product.setBranch(branch);
                            return product;
                        })
                        .then(Mono.just(product)));
    }

    @Override
    public Mono<Product> updateName(Long idProduct, String name) {
        return getProduct(idProduct)
                .map(franchise -> {
                    franchise.setName(name);
                    return franchise;
                })
                .flatMap(productPersistencePort::saveProduct);
    }
}
