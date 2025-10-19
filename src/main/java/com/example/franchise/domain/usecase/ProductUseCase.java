package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IProductServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final IBranchServicePort branchServicePort;

    @Override
    public Mono<Product> addProduct(Product product) {
        log.info("Attempting to add new product: {}", product);
        return branchServicePort
                .getBranch(product.getBranch().getId())
                .doOnNext(branch -> log.debug("Branch found for product creation: {}", branch))
                .flatMap(branch -> productPersistencePort.saveProduct(product))
                .doOnSuccess(saved -> log.info("Product successfully added with id={}", saved.getId()))
                .doOnError(error -> log.error("Error adding product: {}", error.getMessage(), error));
    }

    @Override
    public Mono<Void> deleteProduct(Long idProduct) {
        log.info("Attempting to delete product with id={}", idProduct);
        return productPersistencePort.deleteProduct(idProduct)
                .doOnSuccess(v -> log.info("Product deleted successfully: id={}", idProduct))
                .doOnError(error -> log.error("Error deleting product id={}: {}", idProduct, error.getMessage(), error));
    }

    @Override
    public Mono<Product> updateStock(Long idProduct, Integer stock) {
        log.info("Updating stock for product id={} with new value={}", idProduct, stock);
        return getProduct(idProduct)
                .map(product -> {
                    log.debug("Product retrieved for stock update: {}", product);
                    product.setStock(stock);
                    return product;
                })
                .flatMap(productPersistencePort::saveProduct)
                .doOnSuccess(updated -> log.info("Stock updated successfully for product id={}", updated.getId()))
                .doOnError(error -> log.error("Error updating stock for product id={}: {}", idProduct, error.getMessage(), error));
    }

    @Override
    public Mono<Product> getProduct(Long idProduct) {
        log.debug("Retrieving product with id={}", idProduct);
        return productPersistencePort
                .getProduct(idProduct)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Product not found with id={}", idProduct);
                    return Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_FOUND, idProduct.toString()));
                }))
                .doOnSuccess(product -> log.info("Product found: {}", product))
                .doOnError(error -> log.error("Error retrieving product id={}: {}", idProduct, error.getMessage(), error));
    }

    @Override
    public Flux<Product> getBestByFranchise(Long idFranchise) {
        log.info("Getting best product(s) for franchise id={}", idFranchise);
        return productPersistencePort
                .getBestProductFromFranchise(idFranchise)
                .flatMap(product -> branchServicePort
                        .getBranch(product.getBranch().getId())
                        .map(branch -> {
                            product.setBranch(branch);
                            return product;
                        })
                        .doOnSuccess(b -> log.debug("Branch data added to product id={}", product.getId()))
                        .then(Mono.just(product)))
                .doOnComplete(() -> log.info("Finished retrieving best products for franchise id={}", idFranchise))
                .doOnError(error -> log.error("Error getting best product for franchise id={}: {}", idFranchise, error.getMessage(), error));
    }

    @Override
    public Mono<Product> updateName(Long idProduct, String name) {
        log.info("Updating name for product id={} to '{}'", idProduct, name);
        return getProduct(idProduct)
                .map(product -> {
                    product.setName(name);
                    return product;
                })
                .flatMap(productPersistencePort::saveProduct)
                .doOnSuccess(updated -> log.info("Product name updated successfully for id={}", updated.getId()))
                .doOnError(error -> log.error("Error updating name for product id={}: {}", idProduct, error.getMessage(), error));
    }
}
