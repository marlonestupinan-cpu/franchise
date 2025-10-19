package com.example.franchise.infrastructure.endpoints;

import com.example.franchise.infrastructure.endpoints.dto.BranchDto;
import com.example.franchise.infrastructure.endpoints.dto.FranchiseDto;
import com.example.franchise.infrastructure.endpoints.dto.ProductDto;
import com.example.franchise.infrastructure.endpoints.dto.ProductWithBranchDto;
import com.example.franchise.infrastructure.endpoints.dto.UpdateNameDto;
import com.example.franchise.infrastructure.endpoints.dto.UpdateStockDto;
import com.example.franchise.infrastructure.endpoints.handler.BranchHandler;
import com.example.franchise.infrastructure.endpoints.handler.FranchiseHandler;
import com.example.franchise.infrastructure.endpoints.handler.ProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/franchise", method = RequestMethod.POST, beanClass = FranchiseHandler.class, beanMethod = "addFranchise",
                    operation = @Operation(operationId = "addFranchise", summary = "Add a new franchise", tags = {"Franchises"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = FranchiseDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Franchise added")}
                    )
            ),
            @RouterOperation(
                    path = "/franchise/{id}/name", method = RequestMethod.PATCH, beanClass = FranchiseHandler.class, beanMethod = "updateName",
                    operation = @Operation(operationId = "updateFranchiseName", summary = "Update a franchise's name", tags = {"Franchises"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateNameDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Name updated", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))}
                    )
            ),
            @RouterOperation(
                    path = "/franchise/{id}/leaderboard", method = RequestMethod.GET, beanClass = ProductHandler.class, beanMethod = "getBestProductsByFranchise",
                    operation = @Operation(operationId = "getBestProductsByFranchise", summary = "Get products with the most stock for a franchise", tags = {"Franchises"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            responses = {@ApiResponse(responseCode = "200", description = "Leaderboard retrieved",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductWithBranchDto.class))))}
                    )
            ),
            @RouterOperation(
                    path = "/branch", method = RequestMethod.POST, beanClass = BranchHandler.class, beanMethod = "addBranch",
                    operation = @Operation(operationId = "addBranch", summary = "Add a new branch", tags = {"Branches"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BranchDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Branch added")}
                    )
            ),
            @RouterOperation(
                    path = "/branch/{id}/name", method = RequestMethod.PATCH, beanClass = BranchHandler.class, beanMethod = "updateName",
                    operation = @Operation(operationId = "updateBranchName", summary = "Update a branch's name", tags = {"Branches"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateNameDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Name updated", content = @Content(schema = @Schema(implementation = BranchDto.class)))}
                    )
            ),
            @RouterOperation(
                    path = "/product", method = RequestMethod.POST, beanClass = ProductHandler.class, beanMethod = "addProduct",
                    operation = @Operation(operationId = "addProduct", summary = "Add a new product", tags = {"Products"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ProductDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Product added")}
                    )
            ),
            @RouterOperation(
                    path = "/product/{id}", method = RequestMethod.DELETE, beanClass = ProductHandler.class, beanMethod = "deleteProduct",
                    operation = @Operation(operationId = "deleteProduct", summary = "Delete a product by ID", tags = {"Products"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            responses = {@ApiResponse(responseCode = "200", description = "Product deleted")}
                    )
            ),
            @RouterOperation(
                    path = "/product/{id}/stock", method = RequestMethod.PATCH, beanClass = ProductHandler.class, beanMethod = "updateStock",
                    operation = @Operation(operationId = "updateStock", summary = "Update a product's stock", tags = {"Products"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateStockDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Stock updated", content = @Content(schema = @Schema(implementation = ProductDto.class)))}
                    )
            ),
            @RouterOperation(
                    path = "/product/{id}/name", method = RequestMethod.PATCH, beanClass = ProductHandler.class, beanMethod = "updateName",
                    operation = @Operation(operationId = "updateProductName", summary = "Update a product's name", tags = {"Products"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true)},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateNameDto.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Name updated", content = @Content(schema = @Schema(implementation = ProductDto.class)))}
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler
    ) {
        return route(POST("/franchise"), franchiseHandler::addFranchise)
                .andRoute(POST("/branch"), branchHandler::addBranch)
                .andRoute(POST("/product"), productHandler::addProduct)
                .andRoute(DELETE("/product/{id}"), productHandler::deleteProduct)
                .andRoute(PATCH("/product/{id}/stock"), productHandler::updateStock)
                .andRoute(GET("/franchise/{id}/leaderboard"), productHandler::getBestProductsByFranchise)
                .andRoute(PATCH("/franchise/{id}/name"), franchiseHandler::updateName)
                .andRoute(PATCH("/branch/{id}/name"), branchHandler::updateName)
                .andRoute(PATCH("/product/{id}/name"), productHandler::updateName);
    }
}
