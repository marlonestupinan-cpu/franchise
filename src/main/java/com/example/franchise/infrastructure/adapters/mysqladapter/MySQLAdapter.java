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
public class MySQLAdapter implements IFranchisePersistencePort {
    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper entityMapper;

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseRepository
                .save(entityMapper.toEntity(franchise))
                .map(entityMapper::toFranchise);
    }
}
