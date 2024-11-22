package org.ably.farm_management.vm;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDatailVM {


    @Min(value = 1, message = "The quantity must More than 1")
    private double quantity;

    @Min(value = 1, message = "The price must be number")
    private Long harvestId;


    @Min(value = 1, message = "The price must be number")
    private Long treeId;
}
