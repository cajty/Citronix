package org.ably.farm_management.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Tree;
import org.ably.farm_management.domain.enums.TreeStatus;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.TreeMapper;
import org.ably.farm_management.repository.TreeRepository;
import org.ably.farm_management.service.FieldService;
import org.ably.farm_management.service.TreeService;
import org.ably.farm_management.util.determineTreeStatusUtil;
import org.ably.farm_management.vm.TreeVM;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
   private final FieldService fieldService ;

    @Override
    public TreeDTO save(Tree tree) {
        log.info("Tree saved: {}", tree.getId());
        tree.setStatus(determineTreeStatusUtil.determineTreeStatus(tree.getPlantedAt()));
        return treeMapper.entityToDTO(treeRepository.save(tree));
    }

    @Override
    @Transactional
    public TreeDTO create( TreeVM treeVM) {
        fieldService.existsById(treeVM.getFieldId());
        validateField(treeVM.getFieldId());
        Tree tree = treeMapper.vmToEntity(treeVM);
        log.info("Tree created: {}", tree.getId());
        return save(tree);
    }





    @Override
    @Transactional
    public TreeDTO update(Long id,TreeVM treeVM) {
        existsById(id); // check if tree exists
        fieldService.existsById(treeVM.getFieldId());
        validateField(treeVM.getFieldId());
        Tree updatedTree = treeMapper.vmToEntity(treeVM);

        updatedTree.setId(id);
        log.info("Tree updated: {}", updatedTree);
        return save(updatedTree);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        existsById(id);
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
        if(!treeRepository.existsById(id)){
            throw new BusinessException("Tree not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

    }



    // creat cronjob to update tree status
  @Scheduled(cron = "0 0 0 1 * *")
    public void updateTreeStatus() {
        List<Tree> trees = treeRepository.findAll();

        for(Tree tree : trees){
            TreeStatus status = determineTreeStatusUtil.determineTreeStatus(tree.getPlantedAt());
            tree.setStatus(status);
            treeRepository.save(tree);
            log.info("Tree status updated");
        }
    }


    private void validateField(Long  fieldId) {
        List<String> errors = validateFarmError(fieldId);
        if (!errors.isEmpty()) {
            throw new BusinessException(errors.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    private List<String> validateFarmError(Long  fieldId) {
        double fieldArea = fieldService.findAreaById(fieldId);
        double trees =treeRepository.countByFieldId(fieldId);
        List<String> errors = new ArrayList<>();
        if (fieldArea / trees + 1 >= 100){ // 10 arbres par 1 000 m²). 1000/10 = 100
            errors.add("Farm can have maximum 5 fields");
        }
        return errors;
    }
}





