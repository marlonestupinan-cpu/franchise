package com.example.franchise.infrastructure.endpoints.mappers;

import com.example.franchise.domain.model.Franchise;
import com.example.franchise.infrastructure.endpoints.dto.FranchiseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IFranchiseDtoMapper {
    Franchise toFranchise(FranchiseDto franchiseDto);
    FranchiseDto toDto(Franchise franchise);
}
