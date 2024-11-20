package org.ably.farm_management.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.FarmMapper;
import org.ably.farm_management.repository.FarmRepository;
import org.ably.farm_management.service.FarmService;
import org.ably.farm_management.vm.FarmVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    @Override
    @Transactional
    public FarmDTO save(FarmVM farmVM) {

        Farm farm = farmMapper.vmToEntity(farmVM);
        Farm savedFarm = farmRepository.save(farm);

        log.info("Farm created: {}", savedFarm.getName());
        return farmMapper.entityToDTO(savedFarm);
    }

    @Override
    @Transactional
    public FarmDTO update(Long id,FarmVM farmVM) {

        existsById(id); // check if farm exists

        Farm updatedFarm = farmRepository.save(farmMapper.vmToEntity(farmVM));

        log.info("Farm updated: {}", updatedFarm.getName());
        return farmMapper.entityToDTO(updatedFarm);
    }

    @Override
    @Transactional
    public void delete( Long id) {

        existsById(id); // check if farm exists
        farmRepository.deleteById(id);
        log.info("Farm deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmDTO findById(Long id) {
        return farmRepository.findById(id)
                .map(farmMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Farm not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmDTO> findAll() {
        List<Farm> farms = farmRepository.findAll();

        if (farms.isEmpty()) {
            log.warn("No farms found in the database");
        }

        return farmMapper.toDTOList(farms);
    }

    @Override
    public void existsById(Long id) {
        if(!farmRepository.existsById(id)){
            throw new BusinessException("Farm not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

}