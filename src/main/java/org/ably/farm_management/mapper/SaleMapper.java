package org.ably.farm_management.mapper;

import org.ably.farm_management.domain.entity.Sale;
import org.ably.farm_management.dto.SaleDTO;
import org.ably.farm_management.vm.SaleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    Sale vmToEntity(SaleVM saleVM);

    SaleDTO entityToDTO(Sale sale);

    List<SaleDTO> toDTOList(List<Sale> sales);
}