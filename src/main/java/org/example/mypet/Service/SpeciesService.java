package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Pet.SpeciesDTO;
import org.example.mypet.Models.Species;
import org.example.mypet.Repository.SpeciesRepository;
import org.example.mypet.Utils.Mapper.SpeciesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    public List<SpeciesDTO> findAll() {
        return speciesRepository.findAll().stream()
                .map(SpeciesMapper::toDTO)
                .toList();
    }

    public SpeciesDTO createSpecies(SpeciesDTO dto) {
        if (speciesRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Species with this name already exists");
        }
        Species saved = speciesRepository.save(SpeciesMapper.toEntity(dto));
        return SpeciesMapper.toDTO(saved);
    }

    public SpeciesDTO updateSpecies(SpeciesDTO dto) {
        Species species = speciesRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Species with this id does not exist"));

        species.setName(dto.getName());
        Species updated = speciesRepository.save(species);
        return SpeciesMapper.toDTO(updated);
    }

    public SpeciesDTO deleteSpecies(Long id) {
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Species with this id does not exist"));

        speciesRepository.delete(species);
        return SpeciesMapper.toDTO(species);
    }
}

