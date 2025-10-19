package com.example.franchise.domain.usecase;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.spi.IProductPersistencePort;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private IBranchServicePort branchServicePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @Test
    @DisplayName("addProduct should save product when branch exists")
    void addProduct_shouldSaveProduct_whenBranchExists() {
        Branch existingBranch = new Branch();
        existingBranch.setId(1L);

        Product productToSave = new Product();
        productToSave.setBranch(existingBranch);
        productToSave.setName("Product A");

        Product savedProduct = new Product();
        savedProduct.setId(10L);
        savedProduct.setBranch(existingBranch);
        savedProduct.setName("Product A");

        when(branchServicePort.getBranch(1L)).thenReturn(Mono.just(existingBranch));
        when(productPersistencePort.saveProduct(any(Product.class))).thenReturn(Mono.just(savedProduct));

        Mono<Product> result = productUseCase.addProduct(productToSave);

        StepVerifier.create(result)
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    @DisplayName("addProduct should return error when branch does not exist")
    void addProduct_shouldReturnError_whenBranchDoesNotExist() {
        Branch nonExistentBranch = new Branch();
        nonExistentBranch.setId(99L);
        Product productToSave = new Product();
        productToSave.setBranch(nonExistentBranch);

        when(branchServicePort.getBranch(99L)).thenReturn(Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)));

        Mono<Product> result = productUseCase.addProduct(productToSave);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(productPersistencePort, never()).saveProduct(any(Product.class));
    }

    @Test
    @DisplayName("deleteProduct should complete successfully")
    void deleteProduct_shouldComplete() {
        when(productPersistencePort.deleteProduct(1L)).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.deleteProduct(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    @DisplayName("updateStock should update and save when product exists")
    void updateStock_shouldUpdateAndSave_whenProductExists() {
        Long productId = 1L;
        Integer newStock = 50;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setStock(20);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setStock(newStock);

        when(productPersistencePort.getProduct(productId)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.saveProduct(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        Mono<Product> result = productUseCase.updateStock(productId, newStock);

        StepVerifier.create(result)
                .expectNextMatches(product -> product.getStock().equals(newStock))
                .verifyComplete();
    }

    @Test
    @DisplayName("getProduct should return product when found")
    void getProduct_shouldReturnProduct_whenFound() {
        Product foundProduct = new Product();
        foundProduct.setId(1L);

        when(productPersistencePort.getProduct(1L)).thenReturn(Mono.just(foundProduct));

        Mono<Product> result = productUseCase.getProduct(1L);

        StepVerifier.create(result)
                .expectNext(foundProduct)
                .verifyComplete();
    }

    @Test
    @DisplayName("getProduct should return error when not found")
    void getProduct_shouldReturnError_whenNotFound() {
        when(productPersistencePort.getProduct(anyLong())).thenReturn(Mono.empty());

        Mono<Product> result = productUseCase.getProduct(99L);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }


    @Test
    @DisplayName("updateName should update name and save when product exists")
    void updateName_shouldUpdateAndSave_whenProductExists() {
        Long productId = 1L;
        String newName = "New name";

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(newName);

        when(productPersistencePort.getProduct(productId)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.saveProduct(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        Mono<Product> result = productUseCase.updateName(productId, newName);

        StepVerifier.create(result)
                .expectNextMatches(product -> product.getName().equals(newName))
                .verifyComplete();
    }
}