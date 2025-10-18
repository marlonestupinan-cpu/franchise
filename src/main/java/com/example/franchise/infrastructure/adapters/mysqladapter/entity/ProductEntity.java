package com.example.franchise.infrastructure.adapters.mysqladapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("product")
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private Integer stock;
    @Column("id_branch")
    private Long idBranch;
}
