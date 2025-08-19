package org.example.mypet.Controller;

import lombok.RequiredArgsConstructor;
import org.example.mypet.Service.SpeciesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.example.mypet.DTO.Pet.SpeciesDTO;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpeciesDTO>>> getAllSpecies() {
        return ApiResponse.success(
                speciesService.findAll(),
                HttpStatus.OK.value(),
                "Fetched all species successfully"
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SpeciesDTO>> createSpecies(@Valid @RequestBody SpeciesDTO dto) {
        return ApiResponse.success(
                speciesService.createSpecies(dto),
                HttpStatus.CREATED.value(),
                "Species created successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpeciesDTO>> updateSpecies(
            @PathVariable Long id,
            @Valid @RequestBody SpeciesDTO dto) {
        dto.setId(id);
        return ApiResponse.success(
                speciesService.updateSpecies(dto),
                HttpStatus.OK.value(),
                "Species updated successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SpeciesDTO>> deleteSpecies(@PathVariable Long id) {
        return ApiResponse.success(
                speciesService.deleteSpecies(id),
                HttpStatus.OK.value(),
                "Species deleted successfully"
        );
    }
}
