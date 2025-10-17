package com.example.franchise.domain.spi;

import com.example.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistencePort {
    Mono<Franchise> createFranchise(Franchise franchise);
}
