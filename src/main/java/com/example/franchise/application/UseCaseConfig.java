package com.example.franchise.application;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.spi.IBranchPersistencePort;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import com.example.franchise.domain.usecase.BranchUseCase;
import com.example.franchise.domain.usecase.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public IFranchiseServicePort franchiseServicePort(
            IFranchisePersistencePort persistencePort
    ) {
        return new FranchiseUseCase(persistencePort);
    }

    @Bean
    public IBranchServicePort branchServicePort(
            IBranchPersistencePort persistencePort,
            IFranchiseServicePort franchiseServicePort
    ) {
        return new BranchUseCase(persistencePort, franchiseServicePort);
    }
}
