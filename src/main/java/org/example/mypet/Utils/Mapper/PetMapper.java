package org.example.mypet.Utils.Mapper;


import org.example.mypet.DTO.Pet.PetCreateDTO;
import org.example.mypet.DTO.Pet.PetDTO;
import org.example.mypet.Models.Pet;

public class PetMapper {

    public static PetDTO toDTO(Pet pet) {
        return PetDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .speciesId(pet.getSpecies().getId())
                .speciesName(pet.getSpecies().getName())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .gender(pet.getGender())
                .price(pet.getPrice())
                .quantity(pet.getQuantity())
                .description(pet.getDescription())
                .supplierId(pet.getSupplier() != null ? pet.getSupplier().getId() : null)
                .supplierName(pet.getSupplier() != null ? pet.getSupplier().getName() : null)
                .build();
    }

    public static Pet toEntity(PetCreateDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBreed(dto.getBreed());
        pet.setAge(dto.getAge());
        pet.setGender(dto.getGender());
        pet.setPrice(dto.getPrice());
        pet.setQuantity(dto.getQuantity());
        pet.setDescription(dto.getDescription());
        return pet;
    }
}
