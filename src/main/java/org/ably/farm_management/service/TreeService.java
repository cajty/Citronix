package org.ably.farm_management.service;



import org.ably.farm_management.domain.entity.Tree;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.vm.TreeVM;

import java.util.List;

public interface TreeService {
    TreeDTO save(TreeVM treeVM);
    TreeDTO update(Long id,TreeVM treeVM);
    void delete(Long id);
    TreeDTO findById(Long id);
    List<TreeDTO> findAll();
    void existsById(Long id);
}
