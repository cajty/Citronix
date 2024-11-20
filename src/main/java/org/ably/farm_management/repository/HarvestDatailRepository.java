package org.ably.farm_management.repository;


import org.ably.farm_management.domain.entity.HarvestDatail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestDatailRepository extends JpaRepository<HarvestDatail, Long> {

}
