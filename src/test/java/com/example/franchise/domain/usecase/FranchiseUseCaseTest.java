package com.example.franchise.domain.usecase;

import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.spi.IFranchisePersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FranchiseUseCaseTest {

    @Mock
    private IFranchisePersistencePort franchisePersistencePort;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    @Test
    @DisplayName("addFranchise should call persistence port and return saved franchise")
    void addFranchise_shouldSaveAndReturnFranchise() {
        Franchise franchiseToSave = new Franchise();
        franchiseToSave.setName("Pizza A");

        Franchise savedFranchise = new Franchise();
        savedFranchise.setId(1L);
        savedFranchise.setName("Pizza A");

        OngoingStubbing<Mono<Franchise>> monoOngoingStubbing = when(franchisePersistencePort.saveFranchise(any(Franchise.class))).thenReturn(Mono.just(savedFranchise));

        Mono<Franchise> result = franchiseUseCase.addFranchise(franchiseToSave);

        StepVerifier.create(result)
                .expectNext(savedFranchise)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).saveFranchise(franchiseToSave);
    }

    @Test
    @DisplayName("getFranchise should return franchise when found")
    void getFranchise_shouldReturnFranchise_whenFound() {
        Franchise foundFranchise = new Franchise();
        foundFranchise.setId(1L);
        foundFranchise.setName("Pizza A");

        when(franchisePersistencePort.getFranchise(1L)).thenReturn(Mono.just(foundFranchise));

        Mono<Franchise> result = franchiseUseCase.getFranchise(1L);

        StepVerifier.create(result)
                .expectNext(foundFranchise)
                .verifyComplete();
    }

    @Test
    @DisplayName("getFranchise should return BusinessException when not found")
    void getFranchise_shouldReturnError_whenNotFound() {
        when(franchisePersistencePort.getFranchise(anyLong())).thenReturn(Mono.empty());

        Mono<Franchise> result = franchiseUseCase.getFranchise(99L);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("updateName should update name and save when franchise exists")
    void updateName_shouldUpdateAndSave_whenFranchiseExists() {
        Long franchiseId = 1L;
        String newName = "Pizza A";

        Franchise existingFranchise = new Franchise();
        existingFranchise.setId(franchiseId);
        existingFranchise.setName("Old Name");

        Franchise updatedFranchise = new Franchise();
        updatedFranchise.setId(franchiseId);
        updatedFranchise.setName(newName);

        when(franchisePersistencePort.getFranchise(franchiseId)).thenReturn(Mono.just(existingFranchise));
        when(franchisePersistencePort.saveFranchise(any(Franchise.class))).thenReturn(Mono.just(updatedFranchise));

        Mono<Franchise> result = franchiseUseCase.updateName(franchiseId, newName);

        StepVerifier.create(result)
                .expectNextMatches(franchise -> franchise.getName().equals(newName) && franchise.getId().equals(franchiseId))
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).saveFranchise(any(Franchise.class));
    }

    @Test
    @DisplayName("updateName should return BusinessException when franchise does not exist")
    void updateName_shouldReturnError_whenFranchiseDoesNotExist() {
        Long franchiseId = 99L;
        String newName = "New Name";

        when(franchisePersistencePort.getFranchise(franchiseId)).thenReturn(Mono.empty());

        Mono<Franchise> result = franchiseUseCase.updateName(franchiseId, newName);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(franchisePersistencePort, never()).saveFranchise(any(Franchise.class));
    }
}