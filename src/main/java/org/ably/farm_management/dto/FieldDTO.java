package org.ably.farm_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldDTO {

        private Long id ;
        private String name ;
        private double area ;
        private Long farmId ;
        private List<TreeDTO> trees ;
}
