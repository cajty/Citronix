package org.ably.farm_management.criteria;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FarmSearchCriteria {
    private String name;
    private String location;
    private Double area;
    private LocalDate createdAt;

}
