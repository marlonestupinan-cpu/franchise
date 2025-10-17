package com.example.franchise.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

@Getter
@RequiredArgsConstructor
public enum TechnicalMessage {
    INTERNAL_ERROR("500", "Something went wrong, please try again"),
    INVALID_REQUEST("400", "Bad request, please check the data"),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Invalid parameters, please verify the data"),
    FRANCHISE_ADDED("201", "Franchise added successfully"),
    BRANCH_ADDED("201", "Branch added successfully"),
    PRODUCT_ADDED("201", "Product added successfully"),
    FRANCHISE_NOT_FOUND("404", "Franchise with id {} not found"),
    PRODUCT_NOT_FOUND("404", "Product with id {} not found");
    private final String code;
    private final String message;


    public String getMessage(String[] params) {
        return MessageFormatter.arrayFormat(message, params).getMessage();
    }
}
