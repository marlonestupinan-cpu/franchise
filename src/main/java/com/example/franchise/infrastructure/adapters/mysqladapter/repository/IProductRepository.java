package com.example.franchise.infrastructure.adapters.mysqladapter.repository;

import com.example.franchise.infrastructure.adapters.mysqladapter.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
}
