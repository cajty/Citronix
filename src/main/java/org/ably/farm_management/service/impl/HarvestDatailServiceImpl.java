package org.ably.farm_management.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.HarvestDatail;
import org.ably.farm_management.dto.HarvestDatailDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.HarvestDatailMapper;
import org.ably.farm_management.repository.HarvestDatailRepository;
import org.ably.farm_management.service.HarvestDatailService;
import org.ably.farm_management.service.HarvestService;
import org.ably.farm_management.service.TreeService;
import org.ably.farm_management.vm.HarvestDatailVM;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class HarvestDatailServiceImpl implements HarvestDatailService {
    private final HarvestDatailRepository harvestDatailRepository;
    private final HarvestDatailMapper  HarvestDatailMapper;
    private final HarvestService harvestService;
    private final TreeService treeService;


    public HarvestDatailServiceImpl(HarvestDatailRepository harvestDatailRepository, HarvestDatailMapper HarvestDatailMapper, @Lazy HarvestService harvestService, @Lazy TreeService treeService) {
        this.harvestDatailRepository = harvestDatailRepository;
        this.HarvestDatailMapper = HarvestDatailMapper;
        this.harvestService = harvestService;
        this.treeService = treeService;
    }

    @Override
    @Transactional
    public HarvestDatailDTO save(HarvestDatail harvestDatail) {
        log.info("Harvest detail saved: {}", harvestDatail.getId());
        return HarvestDatailMapper.entityToDTO(harvestDatailRepository.save(harvestDatail));
    }

    @Override
    @Transactional
    public HarvestDatailDTO create(HarvestDatailVM harvestDatailVM) {
        harvestService.existsById(harvestDatailVM.getHarvestId());
        treeService.existsById(harvestDatailVM.getTreeId());
        HarvestDatail harvestDatail = HarvestDatailMapper.vmToEntity(harvestDatailVM);
        log.info("Harvest detail created: {}", harvestDatail.getId());
        return save(harvestDatail);
    }





    @Override
    @Transactional
    public HarvestDatailDTO update(Long id, HarvestDatailVM harvestDatailVM) {
        existsById(id); // check if harvest detail exists
        harvestService.existsById(harvestDatailVM.getHarvestId());
        treeService.existsById(harvestDatailVM.getTreeId());
        existsInHarvestSeasonAndYear(harvestDatailVM.getHarvestId(), harvestDatailVM.getTreeId());
        HarvestDatail updatedHarvestDatail = HarvestDatailMapper.vmToEntity(harvestDatailVM);
        updatedHarvestDatail.setId(id);

        log.info("Harvest detail updated: {}", updatedHarvestDatail.getId());
        return save(updatedHarvestDatail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        existsById(id); // check if harvest detail exists
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

    @Override
    public void existsById(Long id) {
        if(!harvestDatailRepository.existsById(id)){
            throw new BusinessException("Harvest detail not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    private void existsInHarvestSeasonAndYear(Long harvestId, Long treeId) {
        if (harvestDatailRepository.existsInHarvestSeasonAndYear(harvestId, treeId)) {
            throw new BusinessException("Harvest detail already exists in the harvest season and year", HttpStatus.CONFLICT);
        }
    }


}