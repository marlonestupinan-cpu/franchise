package com.example.franchise.infrastructure.adapters.mysqladapter.repository;

import com.example.franchise.infrastructure.adapters.mysqladapter.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
}
