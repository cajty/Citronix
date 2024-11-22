package org.ably.farm_management.repository;



import org.ably.farm_management.domain.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {

Integer countByFieldId(Long fieldId);



}
