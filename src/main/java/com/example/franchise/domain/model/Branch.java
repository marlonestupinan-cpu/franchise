package com.example.franchise.domain.model;

import lombok.Data;

@Data
public class Branch {
    private Long id;
    private String name;
    private Franchise franchise;
}
