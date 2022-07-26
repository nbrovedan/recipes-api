package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.dto.CreateUpdateUnitDTO;
import com.candidate.naidion.recipes.dto.UnitDTO;
import com.candidate.naidion.recipes.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Units", description = "Manage units of measurement")
@RestController
@RequestMapping(value = "/units", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UnitResource {

    private final UnitService unitService;

    @Operation(summary = "Show all units")
    @GetMapping
    public List<UnitDTO> listUnits(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String initials){
        return unitService.listAll(name, initials);
    }

    @Operation(summary = "Create a new unit")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateUpdateUnitDTO createUpdateUnitDTO){
        unitService.create(createUpdateUnitDTO);
    }

    @Operation(summary = "Get an unit")
    @GetMapping("/{id}")
    public UnitDTO listUnits(@PathVariable Integer id){
        return unitService.findById(id);
    }

    @Operation(summary = "Update an unit")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUnit(@PathVariable Integer id,
                           @Valid @RequestBody CreateUpdateUnitDTO createUpdateUnitDTO){
        unitService.update(id, createUpdateUnitDTO);
    }
}
