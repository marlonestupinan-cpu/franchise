package com.example.franchise.infrastructure.adapters.mysqladapter;

import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.spi.IProductPersistencePort;
import com.example.franchise.infrastructure.adapters.mysqladapter.mappers.IProductEntityMapper;
import com.example.franchise.infrastructure.adapters.mysqladapter.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MySQLProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductEntityMapper entityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository
                .save(entityMapper.toEntity(product))
                .map(entityMapper::toProduct);
    }

    @Override
    public Mono<Void> deleteProduct(Long idProduct) {
        return productRepository.deleteById(idProduct);
    }

    @Override
    public Mono<Product> getProduct(Long idProduct) {
        return productRepository
                .findById(idProduct)
                .map(entityMapper::toProduct);
    }
}
