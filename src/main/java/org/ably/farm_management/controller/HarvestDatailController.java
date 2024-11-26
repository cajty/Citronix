package org.ably.farm_management.controller;

import jakarta.validation.Valid;
import org.ably.farm_management.dto.HarvestDatailDTO;
import org.ably.farm_management.service.HarvestDatailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ably.farm_management.vm.HarvestDatailVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/harvestDatail")
@AllArgsConstructor
@Tag(name = "Harvest Datail Controller", description = "Harvest Datail management APIs")
public class HarvestDatailController {

    private final HarvestDatailService harvestDatailService;

    @Operation(summary = "Create new harvest datail")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public HarvestDatailDTO create(@Valid @RequestBody HarvestDatailVM harvestDatailVM) {
        return harvestDatailService.create(harvestDatailVM);
    }

    @Operation(summary = "Get all harvest datails")
    @GetMapping("/")
    public List<HarvestDatailDTO> findAll() {
        return harvestDatailService.findAll();
    }

    @Operation(summary = "Delete harvest datail by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        harvestDatailService.delete(id);
    }

    @Operation(summary = "Update harvest datail by ID")
    @PutMapping("/update/{id}")
    public HarvestDatailDTO update(@PathVariable Long id, @RequestBody @Valid HarvestDatailVM harvestDatailVM) {
        return harvestDatailService.update(id, harvestDatailVM);
    }

    @Operation(summary = "Get harvest datail by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public HarvestDatailDTO findById(@PathVariable final Long id) {
        return harvestDatailService.findById(id);
    }
}