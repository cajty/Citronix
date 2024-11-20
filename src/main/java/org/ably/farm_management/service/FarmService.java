package org.ably.farm_management.service;

import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.vm.FarmVM;

import java.util.List;

public interface FarmService {

    FarmDTO save(FarmVM farmVM);
    FarmDTO update(Long id,FarmVM farmVM);
    void delete(Long id);
    FarmDTO findById(Long id);
    List<FarmDTO> findAll();
    void existsById(Long id);

}