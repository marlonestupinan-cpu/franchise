package com.example.franchise.infrastructure.endpoints;

import com.example.franchise.infrastructure.endpoints.handler.BranchHandler;
import com.example.franchise.infrastructure.endpoints.handler.FranchiseHandler;
import com.example.franchise.infrastructure.endpoints.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler
    ) {
        return route(POST("/franchise"), franchiseHandler::addFranchise)
                .andRoute(POST("/branch"), branchHandler::addBranch)
                .andRoute(POST("/product"), productHandler::addProduct);
    }
}
