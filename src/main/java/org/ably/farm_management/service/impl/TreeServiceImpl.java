package org.ably.farm_management.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Tree;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.TreeMapper;
import org.ably.farm_management.repository.TreeRepository;
import org.ably.farm_management.service.FieldService;
import org.ably.farm_management.service.TreeService;
import org.ably.farm_management.vm.TreeVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
//    private final FieldService fieldService;

    @Override
    @Transactional
    public TreeDTO save( TreeVM treeVM) {
        Tree tree = treeMapper.vmToEntity(treeVM);
        Tree savedTree = treeRepository.save(tree);
        log.info("Tree created: {}", savedTree);
        return treeMapper.entityToDTO(savedTree);
    }

    @Override
    @Transactional
    public TreeDTO update(Long id,TreeVM treeVM) {
//        if(!fieldService.existsById(treeVM.getFieldId())){
//            throw new BusinessException("Field not found with ID: " + treeVM.getFieldId(), HttpStatus.NOT_FOUND);
//        }
        if(!treeRepository.existsById(id)){
            throw new BusinessException("Tree not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        Tree updatedTree = treeRepository.save(treeMapper.vmToEntity(treeVM));
        log.info("Tree updated: {}", updatedTree);
        return treeMapper.entityToDTO(updatedTree);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        if(!treeRepository.existsById(id)){
            throw new BusinessException("Tree not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        treeRepository.deleteById(id);
        log.info("Tree deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public TreeDTO findById(Long id) {
        return treeRepository.findById(id)
                .map(treeMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Tree not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreeDTO> findAll() {
        List<Tree> trees = treeRepository.findAll();
        if (trees.isEmpty()) {
            log.warn("No trees found in the database");
        }
        return treeMapper.toDTOList(trees);
    }

    @Override
    public void existsById(Long id) {

    }
}