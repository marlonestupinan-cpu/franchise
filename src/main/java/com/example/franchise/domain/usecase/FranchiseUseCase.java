package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseServicePort {
    private final IFranchisePersistencePort franchisePersistencePort;

    @Override
    public Mono<Franchise> addFranchise(Franchise franchise) {
        log.info("Attempting to add a new franchise: {}", franchise.getName());
        return franchisePersistencePort.saveFranchise(franchise)
                .doOnSuccess(saved -> log.info("Franchise added successfully with id={}", saved.getId()))
                .doOnError(error -> log.error("Error adding franchise '{}': {}", franchise.getName(), error.getMessage()));
    }

    @Override
    public Mono<Franchise> getFranchise(Long idFranchise) {
        log.info("Fetching franchise with id={}", idFranchise);
        return franchisePersistencePort
                .getFranchise(idFranchise)
                .doOnSuccess(franchise -> {
                    if (franchise != null) {
                        log.info("Franchise found: {}", franchise.getName());
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Franchise with id={} not found", idFranchise);
                    return Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND, idFranchise.toString()));
                }))
                .doOnError(error -> log.error("Error fetching franchise with id={}: {}", idFranchise, error.getMessage()));
    }

    @Override
    public Mono<Franchise> updateName(Long idFranchise, String name) {
        log.info("Updating franchise name for id={} to '{}'", idFranchise, name);
        return getFranchise(idFranchise)
                .map(franchise -> {
                    log.debug("Current franchise name: {}", franchise.getName());
                    franchise.setName(name);
                    return franchise;
                })
                .flatMap(franchisePersistencePort::saveFranchise)
                .doOnSuccess(updated -> log.info("Franchise name updated successfully for id={}", updated.getId()))
                .doOnError(error -> log.error("Error updating franchise name for id={}: {}", idFranchise, error.getMessage()));
    }
}
