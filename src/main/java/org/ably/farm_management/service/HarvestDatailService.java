package org.ably.farm_management.service;

import org.ably.farm_management.dto.HarvestDatailDTO;
import org.ably.farm_management.vm.HarvestDatailVM;

import java.util.List;

public interface HarvestDatailService {
    HarvestDatailDTO save(HarvestDatailVM harvestDatailVM);
    HarvestDatailDTO update(Long id,HarvestDatailVM harvestDatailVM);
    void delete(Long id);
    HarvestDatailDTO findById(Long id);
    List<HarvestDatailDTO> findAll();
}
