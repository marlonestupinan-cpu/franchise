package com.example.franchise.application;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import com.example.franchise.domain.usecase.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public IFranchiseServicePort franchiseServicePort(
            IFranchisePersistencePort persistencePort
    ){
        return new FranchiseUseCase(persistencePort);
    }
}
