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
public class FarmDTO {

    private Long id ;
    private String name ;
    private String location ;
    private double area ;
    private String createdAt ;
    private List<FieldDTO> fields ;

}
