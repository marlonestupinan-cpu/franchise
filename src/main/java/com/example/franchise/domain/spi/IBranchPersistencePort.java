package com.example.franchise.domain.spi;

import com.example.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {
    Mono<Branch> saveBranch(Branch branch);

    Mono<Branch> getBranch(Long idBranch);
}
