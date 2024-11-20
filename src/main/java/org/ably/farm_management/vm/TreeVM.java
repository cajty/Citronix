package org.ably.farm_management.vm;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.enums.TreeStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeVM {
//    private Long id;

    @NotNull(message = "The planting date is required")
    private LocalDate plantedAt;

    @NotNull(message = "The status is required")
    private TreeStatus status;

    @Min(value = 1, message = "The price must be number")
    private Long fieldId;
}