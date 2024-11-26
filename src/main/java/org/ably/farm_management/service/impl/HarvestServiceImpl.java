package org.ably.farm_management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.domain.entity.Harvest;
import org.ably.farm_management.domain.entity.Tree;
import org.ably.farm_management.domain.enums.SeasonType;
import org.ably.farm_management.domain.enums.TreeStatus;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.HarvestMapper;
import org.ably.farm_management.repository.HarvestRepositotry;
import org.ably.farm_management.service.FarmService;
import org.ably.farm_management.service.HarvestDatailService;
import org.ably.farm_management.service.HarvestService;
import org.ably.farm_management.service.TreeService;
import org.ably.farm_management.util.TreeProductivityUtil;
import org.ably.farm_management.util.determineSeasonUtil;
import org.ably.farm_management.vm.HarvestDatailVM;
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
    private final FarmService farmService;
    private final HarvestDatailService harvestDatailService;


    @Override
    public HarvestDTO save(Harvest harvest) {
        return harvestMapper.entityToDTO(harvestRepositotry.save(harvest));
    }


    @Override
    @Transactional
    public HarvestDTO create(HarvestVM harvestVM) {
        FarmDTO farm = farmService.findById(harvestVM.getFarmId());
        SeasonType status = determineSeasonUtil.determineSeason(harvestVM.getDate());
        Harvest harvest = harvestRepositotry.save(Harvest.builder()
                .status(status)
                .date(harvestVM.getDate())
                .build());

        List<TreeDTO> trees = farm.getFields().stream()
                .flatMap(field -> field.getTrees().stream())
                .toList();


        double quantityTotal = 0;

        for (TreeDTO tree : trees) {
           if(harvestDatailService.existsInSeasonAndYear(harvest.getId(), tree.getId())){
               double quantity = TreeProductivityUtil.calculateProductivity(tree.getStatus());
               quantityTotal += quantity;
               harvestDatailService.create(HarvestDatailVM.builder()
                       .harvestId(harvest.getId())
                       .treeId(tree.getId())
                       .quantity(quantity)
                       .build());
           }

        }

        harvest.setQuantityTotal(quantityTotal);

        log.info("Harvest created: {}", harvest.getId());
        return save(harvest);
    }


    @Override
    @Transactional
    public HarvestDTO update(Long id, HarvestVM harvestVM) {
        existsById(id);
        Harvest updatedHarvest = harvestMapper.vmToEntity(harvestVM);
        updatedHarvest.setId(id);
        log.info("Harvest updated: {}", updatedHarvest.getId());
        return save(updatedHarvest);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        existsById(id);
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
    @Transactional
    public void existsById(Long id) {
        if (!harvestRepositotry.existsById(id)) {
            throw new BusinessException("Harvest not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

    }

    @Override
    @Transactional
    public void updateQuantity(Long id, double quantity) {
        Harvest harvest = harvestRepositotry.findById(id)
                .orElseThrow(() -> new BusinessException("Harvest not found with ID: " + id, HttpStatus.NOT_FOUND));
        harvest.setQuantityTotal(quantity);
        harvestRepositotry.save(harvest);

    }
}