package com.example.franchise.domain.spi;

import com.example.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {
    Mono<Branch> createBranch(Branch branch);
}
