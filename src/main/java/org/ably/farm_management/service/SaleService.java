package org.ably.farm_management.service;

import org.ably.farm_management.dto.SaleDTO;
import org.ably.farm_management.vm.SaleVM;

import java.util.List;

public interface SaleService {
    SaleDTO save(SaleVM saleVM);
    SaleDTO update(Long id,SaleVM saleVM);
    void delete(Long id);
    SaleDTO findById(Long id);
    List<SaleDTO> findAll();
}
