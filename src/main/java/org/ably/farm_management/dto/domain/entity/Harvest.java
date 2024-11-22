package org.ably.farm_management.dto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.enums.SeasonType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "harvest")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SeasonType status;

    private LocalDate date;

    private double quantityTotal;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HarvestDatail> harvestDatails;

    @OneToMany(mappedBy = "harvest", fetch = FetchType.LAZY)
    private List<Sale> sales;
}
