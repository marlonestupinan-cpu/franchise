package com.example.franchise.domain.api;

import com.example.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {
    Mono<Branch> addBranch(Branch branch);

    Mono<Branch> getBranch(Long idBranch);

    Mono<Branch> updateName(Long idBranch, String name);
}
