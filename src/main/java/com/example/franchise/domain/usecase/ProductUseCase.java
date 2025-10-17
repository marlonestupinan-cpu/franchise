package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IProductServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final IBranchServicePort branchServicePort;

    @Override
    public Mono<Product> addProduct(Product product) {
        return branchServicePort
                .existBranch(product.getBranch().getId())
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_FOUND, product.getBranch().getId().toString())))
                .flatMap(exist -> productPersistencePort.createProduct(product));
    }
}
