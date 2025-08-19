package org.example.mypet.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Pet.PageMeta;
import org.example.mypet.DTO.Pet.PetCreateDTO;
import org.example.mypet.DTO.Pet.PetDTO;
import org.example.mypet.Models.Pet;
import org.example.mypet.Service.PetService;
import org.example.mypet.Utils.ApiResponse;
import org.example.mypet.Utils.Mapper.PetMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetDTO>>> getAllPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Page<Pet> petsPage = petService.getPets(page, size, sort);

        List<PetDTO> content = petsPage.getContent().stream()
                .map(PetMapper::toDTO)
                .toList();

        List<String> sortList = Arrays.stream(sort).toList();

        PageMeta meta = new PageMeta(
                        petsPage.getNumber(),
                        petsPage.getSize(),
                        petsPage.getTotalElements(),
                        petsPage.getTotalPages(),
                sortList,
                petsPage.isLast()
                );

        return ApiResponse.success(
                content,
                200,
                "Fetched pets successfully",
                meta
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PetDTO>> createPet(@Valid @RequestBody PetCreateDTO dto) {
        return ApiResponse.success(
                petService.createPet(dto),
                201,
                "Pet created successfully",
                null
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDTO>> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody PetCreateDTO dto) {
        return ApiResponse.success(
                petService.updatePet(id, dto),
                200,
                "Pet updated successfully",
                null
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDTO>> deletePet(@PathVariable Long id) {
        return ApiResponse.success(
                petService.deletePet(id),
                200,
                "Pet deleted successfully",
                null
        );
    }
}
