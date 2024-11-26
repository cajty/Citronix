package org.ably.farm_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.farm_management.domain.enums.TreeStatus;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeDTO {

    private Long id;
    private LocalDate plantedAt;
    private Integer age;
    private TreeStatus status;
    private Long fieldId;
}
