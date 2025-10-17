package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseServicePort {
    private final IFranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> addFranchise(Franchise franchise) {
        return franchisePersistencePort.createFranchise(franchise);
    }
}
