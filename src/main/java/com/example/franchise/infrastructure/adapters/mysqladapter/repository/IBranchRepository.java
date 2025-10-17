package com.example.franchise.infrastructure.adapters.mysqladapter.repository;

import com.example.franchise.infrastructure.adapters.mysqladapter.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {
}
