package com.challengeLiteralura.literalura.repository;

import com.challengeLiteralura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    //buscar autores vivos en ese año
    @Query("SELECT a FROM Autor a WHERE a.fechaNac <= :ano AND (a.fechaDef >= :ano OR a.fechaDef = 0)")
    List<Autor> findAutoresVivosPorAno(int ano);

}
