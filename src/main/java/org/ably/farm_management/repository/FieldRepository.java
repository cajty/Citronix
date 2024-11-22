package org.ably.farm_management.repository;


import org.ably.farm_management.domain.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

   Long countByFarmId(Long farmId);

   @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm.id = :farmId")
   Integer sumAreaByFarmId(Long farmId);

   @Query("SELECT f.area FROM Field f WHERE f.id = :id")
   Double findAreaById(Long id);





}
