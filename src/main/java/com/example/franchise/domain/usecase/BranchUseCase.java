package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.spi.IBranchPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {
    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<Branch> addBranch(Branch branch) {
        log.info("Attempting to add branch '{}' for franchise id={}", branch.getName(), branch.getFranchise().getId());
        return franchiseServicePort
                .getFranchise(branch.getFranchise().getId())
                .doOnSuccess(franchise -> log.debug("Franchise '{}' found for branch creation", franchise.getName()))
                .flatMap(franchise -> branchPersistencePort.saveBranch(branch))
                .doOnSuccess(saved -> log.info("Branch '{}' added successfully with id={}", saved.getName(), saved.getId()))
                .doOnError(error -> log.error("Error adding branch '{}': {}", branch.getName(), error.getMessage()));
    }

    @Override
    public Mono<Branch> getBranch(Long idBranch) {
        log.info("Fetching branch with id={}", idBranch);
        return branchPersistencePort
                .getBranch(idBranch)
                .doOnSuccess(branch -> {
                    if (branch != null) {
                        log.info("Branch found: {}", branch.getName());
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Branch with id={} not found", idBranch);
                    return Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND, idBranch.toString()));
                }))
                .doOnError(error -> log.error("Error fetching branch with id={}: {}", idBranch, error.getMessage()));
    }

    @Override
    public Mono<Branch> updateName(Long idBranch, String name) {
        log.info("Updating branch name for id={} to '{}'", idBranch, name);
        return getBranch(idBranch)
                .map(branch -> {
                    log.debug("Current branch name: {}", branch.getName());
                    branch.setName(name);
                    return branch;
                })
                .flatMap(branchPersistencePort::saveBranch)
                .doOnSuccess(updated -> log.info("Branch name updated successfully for id={} to '{}'", updated.getId(), updated.getName()))
                .doOnError(error -> log.error("Error updating branch name for id={}: {}", idBranch, error.getMessage()));
    }
}
