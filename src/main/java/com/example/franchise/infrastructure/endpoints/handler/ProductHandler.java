package com.example.franchise.infrastructure.endpoints.handler;

import com.example.franchise.domain.api.IProductServicePort;
import com.example.franchise.infrastructure.endpoints.dto.ProductDto;
import com.example.franchise.infrastructure.endpoints.dto.UpdateNameDto;
import com.example.franchise.infrastructure.endpoints.dto.UpdateStockDto;
import com.example.franchise.infrastructure.endpoints.mappers.IProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franchise.domain.enums.TechnicalMessage.PRODUCT_ADDED;

@Component
@RequiredArgsConstructor
public class ProductHandler extends BaseHandler {
    private final IProductServicePort productServicePort;
    private final IProductDtoMapper productDtoMapper;

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        return request
                .bodyToMono(ProductDto.class)
                .map(productDtoMapper::toProduct)
                .flatMap(productServicePort::addProduct)
                .flatMap(product -> ServerResponse
                        .ok().bodyValue(PRODUCT_ADDED.getMessage()))
                .transform(errorHandler());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long idProduct = Long.valueOf(request.pathVariable("id"));

        return productServicePort
                .deleteProduct(idProduct)
                .flatMap(deleted -> ServerResponse.ok().build())
                .transform(errorHandler());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long idProduct = Long.valueOf(request.pathVariable("id"));
        return request
                .bodyToMono(UpdateStockDto.class)
                .flatMap(updateStockDto -> productServicePort
                        .updateStock(idProduct, updateStockDto.getStock()))
                .map(productDtoMapper::toDto)
                .flatMap(productDto -> ServerResponse
                        .ok()
                        .bodyValue(productDto))
                .transform(errorHandler());
    }

    public Mono<ServerResponse> getBestProductsByFranchise(ServerRequest request) {
        Long idFranchise = Long.valueOf(request.pathVariable("id"));

        return productServicePort
                .getBestByFranchise(idFranchise)
                .map(productDtoMapper::toDtoWithBranch)
                .collectList()
                .flatMap(productWithBranchDtos -> ServerResponse
                        .ok()
                        .bodyValue(productWithBranchDtos)
                )
                .transform(errorHandler());
    }

    public Mono<ServerResponse> updateName(ServerRequest request) {
        Long idProduct = Long.valueOf(request.pathVariable("id"));
        return request
                .bodyToMono(UpdateNameDto.class)
                .flatMap(updateNameDto -> productServicePort
                        .updateName(idProduct, updateNameDto.getName()))
                .map(productDtoMapper::toDto)
                .flatMap(productDto -> ServerResponse
                        .ok()
                        .bodyValue(productDto))
                .transform(errorHandler());
    }
}
