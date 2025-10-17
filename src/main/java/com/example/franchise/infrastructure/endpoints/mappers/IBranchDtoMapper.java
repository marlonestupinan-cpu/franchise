package com.example.franchise.infrastructure.endpoints.mappers;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.infrastructure.endpoints.dto.BranchDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBranchDtoMapper {
    @Mapping(source = "idFranchise", target = "franchise")
    Branch toBranch(BranchDto branchDto);

    BranchDto toDto(Branch branch);

    default Franchise franchiseFromId(Long id) {
        return Franchise.builder().id(id).build();
    }
}
