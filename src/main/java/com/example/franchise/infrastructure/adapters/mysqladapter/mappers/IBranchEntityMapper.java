package com.example.franchise.infrastructure.adapters.mysqladapter.mappers;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.infrastructure.adapters.mysqladapter.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBranchEntityMapper {
    @Mapping(source = "idFranchise", target = "franchise")
    Branch toBranch(BranchEntity branchEntity);
    @Mapping(source = "franchise", target = "idFranchise")
    BranchEntity toEntity(Branch branch);

    default Long getIdFranchise(Franchise franchise) {
        return franchise.getId();
    }
    default Franchise franchiseFromId(Long id) {
        return Franchise.builder().id(id).build();
    }

}
