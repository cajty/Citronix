package org.ably.farm_management.vm;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestVM {
    @NotNull(message = "Date is required")
    private LocalDate date;


    @Min(value = 1, message = "The price must be number")
    private Long FarmId;


}
