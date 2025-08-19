package org.example.mypet.Utils.Mapper;

import org.example.mypet.DTO.Pet.SpeciesDTO;
import org.example.mypet.Models.Species;

public class SpeciesMapper {

    public static SpeciesDTO toDTO(Species species) {
        if (species == null) return null;
        return SpeciesDTO.builder()
                .id(species.getId())
                .name(species.getName())
                .build();
    }

    public static Species toEntity(SpeciesDTO dto) {
        if (dto == null) return null;
        return Species.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}

