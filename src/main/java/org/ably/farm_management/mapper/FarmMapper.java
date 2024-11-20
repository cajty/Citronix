package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.vm.FarmVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd")
    Farm vmToEntity(FarmVM farmVM);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd")
    FarmDTO entityToDTO(Farm farm);

    List<FarmDTO> toDTOList(List<Farm> farms);
}