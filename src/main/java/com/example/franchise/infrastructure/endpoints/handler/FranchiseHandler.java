package com.example.franchise.infrastructure.endpoints.handler;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.infrastructure.endpoints.dto.FranchiseDto;
import com.example.franchise.infrastructure.endpoints.mappers.IFranchiseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franchise.domain.enums.TechnicalMessage.FRANCHISE_ADDED;

@Component
@RequiredArgsConstructor
public class FranchiseHandler extends BaseHandler {
    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseDtoMapper franchiseDtoMapper;

    public Mono<ServerResponse> addFranchise(ServerRequest request) {
        return request
                .bodyToMono(FranchiseDto.class)
                .map(franchiseDtoMapper::toFranchise)
                .flatMap(franchiseServicePort::addFranchise)
                .flatMap(franchise -> ServerResponse
                        .ok()
                        .bodyValue(FRANCHISE_ADDED.getMessage()))
                .transform(errorHandler());
    }
}
