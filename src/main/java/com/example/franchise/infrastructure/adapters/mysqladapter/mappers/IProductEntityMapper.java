package com.example.franchise.infrastructure.adapters.mysqladapter.mappers;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Product;
import com.example.franchise.infrastructure.adapters.mysqladapter.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IProductEntityMapper {
    @Mapping(source = "idBranch", target = "branch")
    Product toProduct(ProductEntity productEntity);
    @Mapping(source = "branch", target = "idBranch")
    ProductEntity toEntity(Product product);

    default Long getIdBranch(Branch branch) {
        return branch.getId();
    }

    default Branch getBranchFromId(Long idBranch) {
        return Branch.builder().id(idBranch).build();
    }
}
