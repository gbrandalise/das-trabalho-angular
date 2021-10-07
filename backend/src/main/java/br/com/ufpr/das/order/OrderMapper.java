package br.com.ufpr.das.order;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order entity);

    void toDTO(Order entity, @MappingTarget OrderDTO dto);

    Order toEntity(OrderDTO dto);
}
