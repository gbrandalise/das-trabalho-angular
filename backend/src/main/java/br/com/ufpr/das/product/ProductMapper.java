package br.com.ufpr.das.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product entity);

    void toDTO(Product entity, @MappingTarget ProductDTO dto);

    Product toEntity(ProductDTO dto);


    
}
