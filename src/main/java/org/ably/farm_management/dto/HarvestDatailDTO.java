package org.ably.farm_management.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.entity.Harvest;
import org.ably.farm_management.domain.entity.Tree;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HarvestDatailDTO {

    private Long id;
    private double quantity;
    private Long harvestId;
    private Long treeId;
}
