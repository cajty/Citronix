package org.ably.farm_management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.HarvestDatail;
import org.ably.farm_management.dto.HarvestDatailDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.HarvestDatailMapper;
import org.ably.farm_management.mapper.HarvestMapper;
import org.ably.farm_management.repository.HarvestDatailRepository;
import org.ably.farm_management.service.HarvestDatailService;
import org.ably.farm_management.vm.HarvestDatailVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class HarvestDatailServiceImpl implements HarvestDatailService {
    private final HarvestDatailRepository harvestDatailRepository;
    private final HarvestDatailMapper  HarvestDatailMapper;

    @Override
    @Transactional
    public HarvestDatailDTO save(HarvestDatailVM harvestDatailVM) {
        HarvestDatail harvestDatail = HarvestDatailMapper.vmToEntity(harvestDatailVM);
        HarvestDatail savedHarvestDatail = harvestDatailRepository.save(harvestDatail);

        log.info("Harvest detail created: {}", savedHarvestDatail.getId());
        return HarvestDatailMapper.entityToDTO(savedHarvestDatail);
    }

    @Override
    @Transactional
    public HarvestDatailDTO update(Long id, HarvestDatailVM harvestDatailVM) {
        if (!harvestDatailRepository.existsById(id)) {
            throw new BusinessException("Harvest detail not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        HarvestDatail updatedHarvestDatail = harvestDatailRepository.save(HarvestDatailMapper.vmToEntity(harvestDatailVM));

        log.info("Harvest detail updated: {}", updatedHarvestDatail.getId());
        return HarvestDatailMapper.entityToDTO(updatedHarvestDatail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!harvestDatailRepository.existsById(id)) {
            throw new BusinessException("Harvest detail not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        harvestDatailRepository.deleteById(id);
        log.info("Harvest detail deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public HarvestDatailDTO findById(Long id) {
        return harvestDatailRepository.findById(id)
                .map(HarvestDatailMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Harvest detail not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestDatailDTO> findAll() {
        List<HarvestDatail> harvestDatails = harvestDatailRepository.findAll();

        if (harvestDatails.isEmpty()) {
            log.warn("No harvest details found in the database");
        }

        return HarvestDatailMapper.toDTOList(harvestDatails);
    }
}