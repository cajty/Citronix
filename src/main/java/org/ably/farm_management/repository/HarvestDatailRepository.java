package org.ably.farm_management.repository;


import org.ably.farm_management.domain.entity.HarvestDatail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestDatailRepository extends JpaRepository<HarvestDatail, Long> {


   @Query("""
        SELECT CASE WHEN COUNT(hd) > 0 THEN true ELSE false END
        FROM HarvestDatail hd
        JOIN hd.harvest h
        WHERE h.status = (SELECT h2.status FROM Harvest h2 WHERE h2.id = :harvestId)
          AND EXTRACT(YEAR FROM h.date) = EXTRACT(YEAR FROM (SELECT h2.date FROM Harvest h2 WHERE h2.id = :harvestId))
          AND hd.tree.id = :treeId
    """)
   boolean existsInHarvestSeasonAndYear(@Param("harvestId") Long harvestId, @Param("treeId") Long treeId);





}
