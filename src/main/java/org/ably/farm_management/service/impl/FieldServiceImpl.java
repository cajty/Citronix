package org.ably.farm_management.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Field;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.FieldMapper;
import org.ably.farm_management.repository.FieldRepository;
import org.ably.farm_management.service.FarmService;
import org.ably.farm_management.service.FieldService;
import org.ably.farm_management.vm.FieldVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final FarmService farmService;

    @Override
    public FieldDTO save(Field field) {
        return fieldMapper.entityToDTO(fieldRepository.save(field));
    }

    @Override
    @Transactional
    public FieldDTO create(FieldVM fieldVM) {
        farmService.existsById(fieldVM.getFarmId());
        validateFarm(fieldVM.getFarmId());
        Field field = fieldMapper.vmToEntity(fieldVM);
        log.info("Field created: {}", field.getName());
        return save(field);
    }


    @Override
    @Transactional
    public FieldDTO update(Long id, FieldVM fieldVM) {
        existsById(id);
        farmService.existsById(fieldVM.getFarmId());
        validateFarm(fieldVM.getFarmId());
        Field field = fieldMapper.vmToEntity(fieldVM);
        field.setId(id);
        log.info("Field updated: {}", field.getName());
        return save(field);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        existsById(id); // check if field exists
        fieldRepository.deleteById(id);
        log.info("Field deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public FieldDTO findById(Long id) {
        return fieldRepository.findById(id).map(fieldMapper::entityToDTO).orElseThrow(() -> new BusinessException("Field not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDTO> findAll() {
        List<Field> fields = fieldRepository.findAll();
        if (fields.isEmpty()) {
            log.warn("No fields found in the database");
        }
        return fieldMapper.toDTOList(fields);
    }

    @Override
    public void existsById(Long id) {
        if (!fieldRepository.existsById(id)) {
            throw new BusinessException("Field not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Double findAreaById(Long id) {
        return fieldRepository.findAreaById(id);
    }


    private void validateFarm(Long farmId) {
        List<String> errors = validateFarmError(farmId);
        if (!errors.isEmpty()) {
            throw new BusinessException(errors.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    private List<String> validateFarmError(Long farmId) {
        Double farmArea = farmService.findAreaById(farmId);
        Integer fieldArea = fieldRepository.sumAreaByFarmId(farmId);
        List<String> errors = new ArrayList<>();
        if (fieldRepository.countByFarmId(farmId) >= 10) {
            errors.add("Farm can have maximum 5 fields");
        }
        if (fieldArea >= farmArea) {
            errors.add("Farm area is full");
        }
        if (farmArea / farmArea <= 0.5) {
            errors.add("Field area is more than 50% of farm area");
        }
        return errors;
    }
}