package com.example.franchise.infrastructure.endpoints.handler;

import com.example.franchise.domain.api.IFranchiseServicePort;
import com.example.franchise.domain.enums.TechnicalMessage;
import com.example.franchise.domain.exceptions.BusinessException;
import com.example.franchise.infrastructure.endpoints.dto.FranchiseDto;
import com.example.franchise.infrastructure.endpoints.mappers.IFranchiseDtoMapper;
import com.example.franchise.infrastructure.endpoints.util.APIResponse;
import com.example.franchise.infrastructure.endpoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;

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
