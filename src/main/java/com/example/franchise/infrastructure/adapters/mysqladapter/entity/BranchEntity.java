package com.example.franchise.infrastructure.adapters.mysqladapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branch")
public class BranchEntity {
    @Id
    private Long id;
    private String name;
    @Column("id_franchise")
    private Long idFranchise;
}
