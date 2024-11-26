package org.ably.farm_management.service;

import org.ably.farm_management.criteria.FarmSearchCriteria;
import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.vm.FarmVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FarmService {

    FarmDTO save(Farm farm);

    FarmDTO create(FarmVM farmVM);

    FarmDTO update(Long id, FarmVM farmVM);

    void delete(Long id);

    FarmDTO findById(Long id);

    Page<FarmDTO> findAll(int page, int size);


    void existsById(Long id);

    Double findAreaById(Long id);


    Page<FarmDTO> search(FarmSearchCriteria criteria, Pageable pageable);

}