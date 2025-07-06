package com.challengue.literatura.challengue_literatura.repository;

import com.challengue.literatura.challengue_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainingIgnoreCase(String titulo);
}
