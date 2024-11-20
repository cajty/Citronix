package org.ably.farm_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.service.FieldService;
import org.ably.farm_management.vm.FieldVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/field")
@AllArgsConstructor
@Tag(name = "Field Controller", description = "Field management APIs")
public class FieldController {

    private final FieldService fieldService;

    @Operation(summary = "Create new field")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FieldDTO create(@Valid @RequestBody FieldVM fieldVM) {
        return fieldService.save(fieldVM);
    }

    @Operation(summary = "Get all fields")
    @GetMapping("/")
    public List<FieldDTO> findAll() {
        return fieldService.findAll();
    }

    @Operation(summary = "Delete field by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        fieldService.delete(id);
    }

    @Operation(summary = "Update field by ID")
    @PutMapping("/update/{id}")
    public FieldDTO update(@PathVariable Long id, @RequestBody @Valid FieldVM fieldVM) {
        return fieldService.update(id, fieldVM);
    }

    @Operation(summary = "Get field by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FieldDTO findById(@PathVariable final Long id) {
        return fieldService.findById(id);
    }
}