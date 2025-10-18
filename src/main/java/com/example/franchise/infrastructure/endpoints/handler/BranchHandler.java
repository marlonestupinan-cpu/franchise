package com.example.franchise.infrastructure.endpoints.handler;

import com.example.franchise.domain.api.IBranchServicePort;
import com.example.franchise.infrastructure.endpoints.dto.BranchDto;
import com.example.franchise.infrastructure.endpoints.dto.UpdateNameDto;
import com.example.franchise.infrastructure.endpoints.mappers.IBranchDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franchise.domain.enums.TechnicalMessage.BRANCH_ADDED;

@Component
@RequiredArgsConstructor
public class BranchHandler extends BaseHandler {
    private final IBranchServicePort branchServicePort;
    private final IBranchDtoMapper branchDtoMapper;
    public Mono<ServerResponse> addBranch(ServerRequest request) {
        return request
                .bodyToMono(BranchDto.class)
                .map(branchDtoMapper::toBranch)
                .flatMap(branchServicePort::addBranch)
                .flatMap(branch -> ServerResponse
                        .ok()
                        .bodyValue(BRANCH_ADDED.getMessage())
                )
                .transform(errorHandler());
    }

    public Mono<ServerResponse> updateName(ServerRequest request) {
        Long idBranch = Long.valueOf(request.pathVariable("id"));
        return request
                .bodyToMono(UpdateNameDto.class)
                .flatMap(updateNameDto -> branchServicePort
                        .updateName(idBranch, updateNameDto.getName()))
                .map(branchDtoMapper::toDto)
                .flatMap(productDto -> ServerResponse
                        .ok()
                        .bodyValue(productDto))
                .transform(errorHandler());
    }
}
