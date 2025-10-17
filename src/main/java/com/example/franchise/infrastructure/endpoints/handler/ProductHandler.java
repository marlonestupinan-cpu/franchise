package com.example.franchise.infrastructure.endpoints.handler;

import com.example.franchise.domain.api.IProductServicePort;
import com.example.franchise.infrastructure.endpoints.dto.ProductDto;
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
    private final IProductDtoMapper iProductDtoMapper;

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        return request
                .bodyToMono(ProductDto.class)
                .map(iProductDtoMapper::toProduct)
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
}
