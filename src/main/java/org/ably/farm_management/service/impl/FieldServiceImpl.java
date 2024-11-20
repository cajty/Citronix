package org.ably.farm_management.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.domain.entity.Field;
import org.ably.farm_management.dto.FarmDTO;
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

import java.awt.*;
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
    @Transactional
    public FieldDTO save( FieldVM fieldVM) {


        Field field = fieldMapper.vmToEntity(fieldVM);
        Field savedField = fieldRepository.save(field);
        log.info("Field created: {}", savedField.getName());
        return fieldMapper.entityToDTO(savedField);
    }

    @Override
    @Transactional
    public FieldDTO update(Long id ,FieldVM fieldVM) {

        existsById(id); // check if field exists
        farmService.existsById(fieldVM.getFarmId());

        Field field = fieldMapper.vmToEntity(fieldVM);

        Field updatedField = fieldRepository.save(field);
        log.info("Field updated: {}", updatedField.getName());
        return fieldMapper.entityToDTO(updatedField);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        existsById(id); // check if field exists
        fieldRepository.deleteById(id);
        log.info("Field deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public FieldDTO findById( Long id) {
        return fieldRepository.findById(id)
                .map(fieldMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Field not found with ID: " + id, HttpStatus.NOT_FOUND));
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

    private List<String> validateFarm( ) {
        List<String> errors = new ArrayList<>();


        return errors;
    }
}