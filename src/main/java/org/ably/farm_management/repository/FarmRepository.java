package org.ably.farm_management.repository;

import org.ably.farm_management.domain.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

   @Query("SELECT f.area FROM Farm f WHERE f.id = :id")
   Double findAreaById(Long id);








}
