package com.example.franchise.infrastructure.adapters.mysqladapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("franchise")
public class FranchiseEntity {
    @Id
    private Long id;
    private String name;
}
