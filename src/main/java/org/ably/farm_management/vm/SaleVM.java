package org.ably.farm_management.vm;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleVM {


    @NotBlank(message = "Client name is required")
    @Size(min = 2, max = 100, message = "Client name must be between 2 and 100 characters")
    private String client;

    @Min(value = 0, message = "Unit price must be positive")
    private double unitPrice;



    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    @Min(value = 1, message = "The price must be number")
    private Long harvestId;
}