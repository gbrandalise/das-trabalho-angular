package br.com.ufpr.das.orderItem;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import br.com.ufpr.das.product.ProductMapper;
import br.com.ufpr.das.purchaseOrder.PurchaseOrderMapper;

@Mapper(uses = {PurchaseOrderMapper.class, ProductMapper.class})
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDTO toDTO(OrderItem entity);

    void toDTO(OrderItem entity, @MappingTarget OrderItemDTO dto);

    OrderItem toEntity(OrderItemDTO dto);
}
