package org.ably.farm_management.vm;




import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
public class FarmVM {



//    @NotNull(groups = UpdateGroup.class, message = "Farm ID is required for update")
//    private Long id;

    @NotBlank(message = "Farm name is required")
    @Size(min = 2, max = 100, message = "Farm name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 200, message = "Location must be between 2 and 200 characters")
    private String location;

    @Min(value = 2000, message = "Farm area must be at least 2,000 m²")
    private double area;

    @Past(message = "Creation date must be in the past")
    private LocalDate createdAt;




}