package com.challengeLiteralura.literalura.repository;

import com.challengeLiteralura.literalura.model.Lenguaje;
import com.challengeLiteralura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String titulo);

    List<Libro>findByLenguaje(Lenguaje lenguaje);
}
