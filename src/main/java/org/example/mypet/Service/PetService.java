package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Pet.PetCreateDTO;
import org.example.mypet.DTO.Pet.PetDTO;
import org.example.mypet.Models.Pet;
import org.example.mypet.Models.Species;
import org.example.mypet.Models.Supplier;
import org.example.mypet.Repository.PetRepository;
import org.example.mypet.Repository.SpeciesRepository;
import org.example.mypet.Repository.SupplierRepository;
import org.example.mypet.Utils.Mapper.PetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final SupplierRepository supplierRepository;

    public Page<Pet> getPets(int page, int size, String[] sort) {
        String sortField = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";

        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equalsIgnoreCase("asc")
                        ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return petRepository.findAll(pageable);
    }

    public PetDTO createPet(PetCreateDTO dto) {
        Species species = speciesRepository.findById(dto.getSpeciesId())
                .orElseThrow(() -> new RuntimeException("Species not found"));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Pet pet = PetMapper.toEntity(dto);
        pet.setSpecies(species);
        pet.setSupplier(supplier);

        return PetMapper.toDTO(petRepository.save(pet));
    }

    public PetDTO updatePet(Long id, PetCreateDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Cập nhật dữ liệu
        pet.setName(dto.getName());
        pet.setAge(dto.getAge());
        pet.setPrice(dto.getPrice());

        if (dto.getSpeciesId() != null) {
            Species species = speciesRepository.findById(dto.getSpeciesId())
                    .orElseThrow(() -> new RuntimeException("Species not found"));
            pet.setSpecies(species);
        }

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            pet.setSupplier(supplier);
        }

        return PetMapper.toDTO(petRepository.save(pet));
    }

    public PetDTO deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        petRepository.delete(pet);
        return PetMapper.toDTO(pet);
    }
}
