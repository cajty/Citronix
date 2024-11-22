package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.Field;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.vm.FieldVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(target = "farm.id", source = "farmId")
    Field vmToEntity(FieldVM fieldVM);

    @Mapping(target = "farmId", source = "farm.id")
    FieldDTO entityToDTO(Field field);

    List<FieldDTO> toDTOList(List<Field> fields);
}