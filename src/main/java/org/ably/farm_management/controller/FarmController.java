package org.ably.farm_management.controller;

import jakarta.validation.Valid;
import org.ably.farm_management.criteria.FarmSearchCriteria;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.service.FarmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ably.farm_management.vm.FarmVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/farm")
@AllArgsConstructor
@Tag(name = "Farm Controller", description = "Farm management APIs")
public class FarmController {

    private final FarmService farmService;

    @Operation(summary = "Create new farm")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FarmDTO create(@Valid @RequestBody FarmVM farmVM) {
        return farmService.create(farmVM);
    }

    @Operation(summary = "Get all farms")
    @GetMapping("/")
   public Page<FarmDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return farmService.findAll(page, size);
}

    @Operation(summary = "Delete farm by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        farmService.delete(id);
    }

    @Operation(summary = "Update farm by ID")
    @PutMapping("/update/{id}")
    public FarmDTO update(@PathVariable Long id, @Valid @RequestBody FarmVM farmVM) {
        return farmService.update(id, farmVM);
    }

    @Operation(summary = "Get farm by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FarmDTO findById(@PathVariable final Long id) {
        return farmService.findById(id);
    }



    @Operation(summary = "Recherche multicritère (Criteria Builder)")
    @GetMapping("/search")
    public ResponseEntity<Page<FarmDTO>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double area,
            @RequestParam(required = false) String createdAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        FarmSearchCriteria criteria = FarmSearchCriteria.builder()
                .name(name)
                .location(location)
                .area(area)
                .createdAt(createdAt != null ? LocalDate.parse(createdAt) : null)
                .build();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FarmDTO> farms = farmService.search(criteria, pageable);
        return ResponseEntity.ok(farms);
    }

}