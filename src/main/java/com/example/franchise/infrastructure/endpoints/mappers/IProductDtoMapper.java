package com.example.franchise.infrastructure.endpoints.mappers;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Product;
import com.example.franchise.infrastructure.endpoints.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IProductDtoMapper {
    @Mapping(source = "idBranch", target = "branch")
    Product toProduct(ProductDto productDto);
    @Mapping(source = "branch", target = "idBranch")
    ProductDto toDto(Product product);

    default Branch branchFromId(Long id) {
        return Branch.builder().id(id).build();
    }
    default Long getIdBranch(Branch branch) {
        return branch.getId();
    }
}
