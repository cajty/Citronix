package org.ably.farm_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ably.farm_management.dto.SaleDTO;
import org.ably.farm_management.service.SaleService;
import org.ably.farm_management.vm.SaleVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/sale")
@AllArgsConstructor
@Tag(name = "Sale Controller", description = "Sale management APIs")
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "Create new sale")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SaleDTO create(@Valid @RequestBody SaleVM saleVM) {
        return saleService.create(saleVM);
    }

    @Operation(summary = "Get all sales")
    @GetMapping("/")
    public List<SaleDTO> findAll() {
        return saleService.findAll();
    }

    @Operation(summary = "Delete sale by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        saleService.delete(id);
    }

    @Operation(summary = "Update sale by ID")
    @PutMapping("/update/{id}")
    public SaleDTO update(@PathVariable Long id, @RequestBody @Valid SaleVM saleVM) {
        return saleService.update(id, saleVM);
    }

    @Operation(summary = "Get sale by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public SaleDTO findById(@PathVariable final Long id) {
        return saleService.findById(id);
    }
}