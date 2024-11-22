package org.ably.farm_management.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "harvestdetail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDatail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id")
    private Tree tree;
}
