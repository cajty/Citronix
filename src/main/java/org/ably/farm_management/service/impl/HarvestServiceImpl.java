package org.ably.farm_management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Harvest;
import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.HarvestMapper;
import org.ably.farm_management.repository.HarvestRepositotry;
import org.ably.farm_management.service.HarvestService;
import org.ably.farm_management.vm.HarvestVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepositotry harvestRepositotry;
    private final HarvestMapper harvestMapper;

    @Override
    @Transactional
    public HarvestDTO save(HarvestVM harvestVM) {
        Harvest harvest = harvestMapper.vmToEntity(harvestVM);
        Harvest savedHarvest = harvestRepositotry.save(harvest);

        log.info("Harvest created: {}", savedHarvest.getId());
        return harvestMapper.entityToDTO(savedHarvest);
    }

    @Override
    @Transactional
    public HarvestDTO update(Long id, HarvestVM harvestVM) {
        if (!harvestRepositotry.existsById(id)) {
            throw new BusinessException("Harvest not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        Harvest updatedHarvest = harvestRepositotry.save(harvestMapper.vmToEntity(harvestVM));

        log.info("Harvest updated: {}", updatedHarvest.getId());
        return harvestMapper.entityToDTO(updatedHarvest);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!harvestRepositotry.existsById(id)) {
            throw new BusinessException("Harvest not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        harvestRepositotry.deleteById(id);
        log.info("Harvest deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public HarvestDTO findById(Long id) {
        return harvestRepositotry.findById(id)
                .map(harvestMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Harvest not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestDTO> findAll() {
        List<Harvest> harvests = harvestRepositotry.findAll();

        if (harvests.isEmpty()) {
            log.warn("No harvests found in the database");
        }

        return harvestMapper.toDTOList(harvests);
    }

    @Override
    public void existsById(Long id) {

    }
}