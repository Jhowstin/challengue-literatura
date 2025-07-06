package com.challengue.literatura.challengue_literatura.repository;

import com.challengue.literatura.challengue_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);

    Optional<Autor> findByNombre(String nombre);
}
