package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.spi.IBranchPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchServicePort {
    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<Branch> addBranch(Branch branch) {

        return franchiseServicePort
                .existFranchise(branch.getFranchise().getId())
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND, branch.getFranchise().getId().toString())))
                .flatMap(exist -> branchPersistencePort.createBranch(branch));
    }
}
