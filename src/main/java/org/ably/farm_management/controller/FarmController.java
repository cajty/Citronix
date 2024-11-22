package org.ably.farm_management.controller;

import jakarta.validation.Valid;
import org.ably.farm_management.dto.FarmDTO;
import org.ably.farm_management.service.FarmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ably.farm_management.vm.FarmVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<FarmDTO> findAll() {
        return farmService.findAll();
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
}