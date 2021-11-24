package br.com.ufpr.das.purchaseOrder;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import br.com.ufpr.das.client.ClientMapper;

@Mapper(uses = {ClientMapper.class})
public interface PurchaseOrderMapper {

    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    PurchaseOrderDTO toDTO(PurchaseOrder entity);

    void toDTO(PurchaseOrder entity, @MappingTarget PurchaseOrderDTO dto);

    PurchaseOrder toEntity(PurchaseOrderDTO dto);
}
