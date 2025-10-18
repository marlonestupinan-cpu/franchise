package com.example.franchise.infrastructure.adapters.mysqladapter.repository;

import com.example.franchise.infrastructure.adapters.mysqladapter.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    @Query("""
            SELECT p.* FROM product p
            JOIN branch b ON p.id_branch = b.id
            WHERE
                b.id_franchise = :idFranchise AND
                p.stock = (
                    SELECT MAX(p2.stock)
                    FROM product p2
                    WHERE p2.id_branch = p.id_branch
                )
            """
    )
    Flux<ProductEntity> getBestProductsFromFranchise(Long idFranchise);
}
