package org.ably.farm_management.dto.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.enums.TreeStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tree")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantedAt;

    @Enumerated(EnumType.STRING)
    private TreeStatus status;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HarvestDatail> harvestDatails; // Corrected mappedBy value
}
