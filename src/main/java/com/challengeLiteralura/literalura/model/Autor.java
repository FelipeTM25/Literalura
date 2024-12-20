package com.challengeLiteralura.literalura.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fechaNac;
    private int fechaDef;

    @ManyToMany(mappedBy = "autores")
    private Set<Libro> libros;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNac = datosAutor.fechaNac();
        this.fechaDef = datosAutor.fechaDef();
    }
    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(int fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getFechaDef() {
        return fechaDef;
    }

    public void setFechaDef(int fechaDef) {
        this.fechaDef = fechaDef;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                ", nombre='" + nombre + '\'' +
                ", fechaNac=" + fechaNac +
                ", fechaDef=" + fechaDef +
                '}';
    }
}
