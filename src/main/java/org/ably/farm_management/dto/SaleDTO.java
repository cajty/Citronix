package org.ably.farm_management.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.entity.Harvest;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {

    private Long id;
    private String client;
    private double unitPrice;
    private double revenue;
    private LocalDate date;
    private Harvest harvest;
}
