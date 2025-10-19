package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.spi.IBranchPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @InjectMocks
    private BranchUseCase branchUseCase;

    @Test
    @DisplayName("addBranch should save and return branch when franchise exists")
    void addBranch_shouldSaveAndReturnBranch_whenFranchiseExists() {
        Franchise existingFranchise = new Franchise();
        existingFranchise.setId(1L);

        Branch branchToSave = new Branch();
        branchToSave.setFranchise(existingFranchise);
        branchToSave.setName("Branch A");

        Branch savedBranch = new Branch();
        savedBranch.setId(10L);
        savedBranch.setFranchise(existingFranchise);
        savedBranch.setName("Branch A");

        when(franchiseServicePort.getFranchise(1L)).thenReturn(Mono.just(existingFranchise));
        when(branchPersistencePort.saveBranch(any(Branch.class))).thenReturn(Mono.just(savedBranch));

        Mono<Branch> result = branchUseCase.addBranch(branchToSave);

        StepVerifier.create(result)
                .expectNext(savedBranch)
                .verifyComplete();

        verify(branchPersistencePort, times(1)).saveBranch(branchToSave);
    }

    @Test
    @DisplayName("addBranch should return error when franchise does not exist")
    void addBranch_shouldReturnError_whenFranchiseDoesNotExist() {
        Franchise nonExistentFranchise = new Franchise();
        nonExistentFranchise.setId(99L);
        Branch branchToSave = new Branch();
        branchToSave.setFranchise(nonExistentFranchise);

        when(franchiseServicePort.getFranchise(99L))
                .thenReturn(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)));

        Mono<Branch> result = branchUseCase.addBranch(branchToSave);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(branchPersistencePort, never()).saveBranch(any(Branch.class));
    }

    @Test
    @DisplayName("getBranch should return branch when found")
    void getBranch_shouldReturnBranch_whenFound() {
        Branch foundBranch = new Branch();
        foundBranch.setId(1L);

        when(branchPersistencePort.getBranch(1L)).thenReturn(Mono.just(foundBranch));

        Mono<Branch> result = branchUseCase.getBranch(1L);

        StepVerifier.create(result)
                .expectNext(foundBranch)
                .verifyComplete();
    }

    @Test
    @DisplayName("getBranch should return error when not found")
    void getBranch_shouldReturnError_whenNotFound() {
        when(branchPersistencePort.getBranch(anyLong())).thenReturn(Mono.empty());

        Mono<Branch> result = branchUseCase.getBranch(99L);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("updateName should update name and save when branch exists")
    void updateName_shouldUpdateAndSave_whenBranchExists() {
        Long branchId = 1L;
        String newName = "Branch A";

        Branch existingBranch = new Branch();
        existingBranch.setId(branchId);
        existingBranch.setName("Old Name");

        Branch updatedBranch = new Branch();
        updatedBranch.setId(branchId);
        updatedBranch.setName(newName);

        when(branchPersistencePort.getBranch(branchId)).thenReturn(Mono.just(existingBranch));
        when(branchPersistencePort.saveBranch(any(Branch.class))).thenReturn(Mono.just(updatedBranch));

        Mono<Branch> result = branchUseCase.updateName(branchId, newName);

        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.getName().equals(newName) && branch.getId().equals(branchId))
                .verifyComplete();

        verify(branchPersistencePort, times(1)).saveBranch(any(Branch.class));
    }

    @Test
    @DisplayName("updateName should return error when branch does not exist")
    void updateName_shouldReturnError_whenBranchDoesNotExist() {
        Long branchId = 99L;
        String newName = "New Name";

        when(branchPersistencePort.getBranch(branchId)).thenReturn(Mono.empty());

        Mono<Branch> result = branchUseCase.updateName(branchId, newName);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(branchPersistencePort, never()).saveBranch(any(Branch.class));
    }
}