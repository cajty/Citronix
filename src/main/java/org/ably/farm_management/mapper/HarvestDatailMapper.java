package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.HarvestDatail;
import org.ably.farm_management.dto.HarvestDatailDTO;
import org.ably.farm_management.vm.HarvestDatailVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestDatailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest.id", source = "harvestId")
    @Mapping(target = "tree.id", source = "treeId")
    HarvestDatail vmToEntity(HarvestDatailVM harvestDatailVM);

    HarvestDatailDTO entityToDTO(HarvestDatail harvestDatail);

    List<HarvestDatailDTO> toDTOList(List<HarvestDatail> harvestDatails);


}