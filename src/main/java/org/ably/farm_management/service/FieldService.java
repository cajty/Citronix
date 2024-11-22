package org.ably.farm_management.service;
import org.ably.farm_management.domain.entity.Field;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.vm.FieldVM;

import java.util.List;

public interface FieldService {

    FieldDTO save(Field field);
    FieldDTO create(FieldVM fieldVM);
    FieldDTO update(Long id,FieldVM fieldVM);
    void delete(Long id);
    FieldDTO findById(Long id);
    List<FieldDTO> findAll();
    void existsById(Long id);
    Double findAreaById(Long id);
}
