package org.ably.farm_management.repository;

import org.ably.farm_management.domain.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> , JpaSpecificationExecutor<Farm> {

   @Query("SELECT f.area FROM Farm f WHERE f.id = :id")
   Double findAreaById(Long id);


@Query("SELECT f FROM Farm f WHERE (:name IS NULL OR f.name = :name) or  (:location IS NULL OR f.location = :location) or (:area IS NULL OR f.area = :area)")
List<Farm> search(@Param("name") String name, @Param("location") String location, @Param("area") double area);








}
