package org.ably.farm_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.service.TreeService;
import org.ably.farm_management.vm.TreeVM;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tree")
@AllArgsConstructor
@Tag(name = "Tree Controller", description = "Tree management APIs")
public class TreeController {

    private final TreeService treeService;
    

    @Operation(summary = "Create new tree")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TreeDTO create(@Valid @RequestBody TreeVM treeVM) {
        return treeService.create(treeVM);
    }

    @Operation(summary = "Get all trees")
    @GetMapping("/")
    public List<TreeDTO> findAll() {
        return treeService.findAll();
    }

    @Operation(summary = "Delete tree by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        treeService.delete(id);
    }

    @Operation(summary = "Update tree by ID")
    @PutMapping("/update/{id}")
    public TreeDTO update(@PathVariable Long id, @RequestBody @Valid TreeVM treeVM) {
        return treeService.update(id, treeVM);
    }

    @Operation(summary = "Get tree by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TreeDTO findById(@PathVariable final Long id) {
        return treeService.findById(id);
    }
}