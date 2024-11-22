package org.ably.farm_management.controller;

import jakarta.validation.Valid;
import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.service.HarvestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ably.farm_management.vm.HarvestVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/harvest")
@AllArgsConstructor
@Tag(name = "Harvest Controller", description = "Harvest management APIs")
public class HarvestController {

    private final HarvestService harvestService;

    @Operation(summary = "Create new harvest")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public HarvestDTO create(@Valid @RequestBody HarvestVM harvestVM) {
        return harvestService.create(harvestVM);
    }

    @Operation(summary = "Get all harvests")
    @GetMapping("/")
    public List<HarvestDTO> findAll() {
        return harvestService.findAll();
    }

    @Operation(summary = "Delete harvest by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        harvestService.delete(id);
    }

    @Operation(summary = "Update harvest by ID")
    @PutMapping("/update/{id}")
    public HarvestDTO update(@PathVariable Long id, @RequestBody @Valid HarvestVM harvestVM) {
        return harvestService.update(id, harvestVM);
    }

    @Operation(summary = "Get harvest by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public HarvestDTO findById(@PathVariable final Long id) {
        return harvestService.findById(id);
    }
}