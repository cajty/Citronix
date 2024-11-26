package org.ably.farm_management.vm;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldVM {


    @Size(min = 2, max = 100, message = "Le nom du champ doit être compris entre 2 et 100 caractères")
    private String name;

    @Min(value = 1000, message = "La superficie du champ doit être supérieure à 1000 m²")
    private double area;

    @Min(value = 1, message = "The price must be negative")
    private Long farmId;


}