package com.example.franchise.infrastructure.adapters.mysqladapter;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.spi.IBranchPersistencePort;
import com.example.franchise.infrastructure.adapters.mysqladapter.mappers.IBranchEntityMapper;
import com.example.franchise.infrastructure.adapters.mysqladapter.repository.IBranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MySQLBranchAdapter implements IBranchPersistencePort {
    private final IBranchRepository branchPersistencePort;
    private final IBranchEntityMapper entityMapper;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return branchPersistencePort
                .save(entityMapper.toEntity(branch))
                .map(entityMapper::toBranch);
    }

    @Override
    public Mono<Branch> getBranch(Long idBranch) {
        return branchPersistencePort
                .findById(idBranch)
                .map(entityMapper::toBranch);
    }
}
