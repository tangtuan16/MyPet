package org.example.mypet.Repository;

import org.example.mypet.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByName(String name);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    List<Supplier> findAllByActiveTrue();
}
