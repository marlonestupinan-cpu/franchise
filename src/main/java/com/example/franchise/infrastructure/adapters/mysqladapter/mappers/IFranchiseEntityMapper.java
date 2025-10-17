package com.example.franchise.infrastructure.adapters.mysqladapter.mappers;

import com.example.franchise.domain.model.Franchise;
import com.example.franchise.infrastructure.adapters.mysqladapter.entity.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IFranchiseEntityMapper {
    Franchise toFranchise(FranchiseEntity franchiseEntity);
    FranchiseEntity toEntity(Franchise franchise);
}
