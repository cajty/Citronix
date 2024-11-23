package org.ably.farm_management.vm;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.validator.MonthAllowed;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeVM {


    @NotNull(message = "The planting date is required")
    @MonthAllowed(message = "Planting is only allowed in March and May")
    @PastOrPresent(message = "Date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate plantedAt;


    @Min(value = 1, message = "The price must be number")
    private Long fieldId;
}