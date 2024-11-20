package org.ably.farm_management.service;

import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.vm.HarvestVM;

import java.util.List;

public interface HarvestService {
    HarvestDTO save(HarvestVM harvestVM);
    HarvestDTO update(Long id,HarvestVM harvestVM);
    void delete(Long id);
    HarvestDTO findById(Long id);
    List<HarvestDTO> findAll();
    void existsById(Long id);
}
