package org.ably.farm_management.repository;


import org.ably.farm_management.domain.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

   long countByFarmId(Long farmId);

}
