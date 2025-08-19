package org.example.mypet.Repository;

import org.example.mypet.Models.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    boolean existsByName(String name);

    boolean existsById(Long id);
}
