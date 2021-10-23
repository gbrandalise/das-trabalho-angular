package br.com.ufpr.das.client;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDTO(Client entity);

    void toDTO(Client entity, @MappingTarget ClientDTO dto);

    Client toEntity(ClientDTO dto);
    
}
