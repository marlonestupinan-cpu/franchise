package com.example.franchise.infrastructure.adapters.mysqladapter;

import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import com.example.franchise.infrastructure.adapters.mysqladapter.mappers.IFranchiseEntityMapper;
import com.example.franchise.infrastructure.adapters.mysqladapter.repository.IFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MySQLFranchiseAdapter implements IFranchisePersistencePort {
    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper entityMapper;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository
                .save(entityMapper.toEntity(franchise))
                .map(entityMapper::toFranchise);
    }

    @Override
    public Mono<Franchise> getFranchise(Long idFranchise) {
        return franchiseRepository
                .findById(idFranchise)
                .map(entityMapper::toFranchise);
    }
}
