package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.Tree;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.vm.TreeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    @Mapping(target = "field.id", source = "fieldId")
    Tree vmToEntity(TreeVM treeVM);

    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "age", expression = "java(java.time.LocalDate.now().getYear() - tree.getPlantedAt().getYear())")
    TreeDTO entityToDTO(Tree tree);


    List<TreeDTO> toDTOList(List<Tree> trees);
}