package com.example.franchise.infrastructure.endpoints.dto;

import lombok.Data;

@Data
public class ProductWithBranchDto {
    private Long id;
    private String name;
    private Integer stock;
    private BranchDto branch;
}
