package org.ably.farm_management.vm;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldVM {
//    private Long id;

    @NotBlank(message = "Le nom du champ est obligatoire")
    private String name;

    @Min(value = 1000, message = "La superficie du champ doit être supérieure à 1000 m²")
    private double area;

    @Min(value = 1, message = "The price must be negative")
    private Long farmId;


}