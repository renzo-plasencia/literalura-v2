package com.aluracursos.literalura.repositorio;

import com.aluracursos.literalura.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros, Long> {
    //Consultas SQL

    Optional<Libros> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Libros l JOIN FETCH l.autores a JOIN FETCH l.idiomas i")
    List<Libros> findAllWithAutoresAndIdiomas();

}
