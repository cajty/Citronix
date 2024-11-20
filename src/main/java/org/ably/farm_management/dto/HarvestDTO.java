package org.ably.farm_management.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ably.farm_management.domain.enums.SeasonType;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HarvestDTO {

    private Long id;


    private SeasonType status;

    private LocalDate date;

    private double quantityTotal;


}
