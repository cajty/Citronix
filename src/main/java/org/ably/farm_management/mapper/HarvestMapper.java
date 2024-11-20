package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.Harvest;
import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.vm.HarvestVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(target = "id", ignore = true)
    Harvest vmToEntity(HarvestVM harvestVM);

    HarvestDTO entityToDTO(Harvest harvest);

    List<HarvestDTO> toDTOList(List<Harvest> harvests);
}