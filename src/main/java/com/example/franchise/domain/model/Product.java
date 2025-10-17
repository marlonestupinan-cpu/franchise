package com.example.franchise.domain.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Integer stock;
    private Branch branch;
}
