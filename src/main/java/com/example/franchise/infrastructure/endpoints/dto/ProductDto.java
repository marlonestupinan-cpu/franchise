package com.example.franchise.infrastructure.endpoints.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Integer stock;
    private Long idBranch;
}
