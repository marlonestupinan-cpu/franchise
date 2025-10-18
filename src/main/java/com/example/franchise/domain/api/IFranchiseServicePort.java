package com.example.franchise.domain.api;

import com.example.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {
    Mono<Franchise> addFranchise(Franchise franchise);

    Mono<Franchise> getFranchise(Long idFranchise);

    Mono<Franchise> updateName(Long idFranchise, String name);
}
