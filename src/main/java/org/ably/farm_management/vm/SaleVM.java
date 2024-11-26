package org.ably.farm_management.vm;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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


    @Min(value = 1, message = "Quantity must be positive")
    private double quantity;

    @PastOrPresent(message = "Date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Min(value = 1, message = "The price must be number")
    private Long harvestId;
}