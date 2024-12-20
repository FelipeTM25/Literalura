package com.challengeLiteralura.literalura.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores;
    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;
    private Integer cantDescargas;


    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.lenguaje = Lenguaje.fromString(datosLibro.lenguajes().get(0).trim());
        this.cantDescargas = datosLibro.cantDescargas();
    }
    public Libro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getCantDescargas() {
        return cantDescargas;
    }

    public void setCantDescargas(Integer cantDescargas) {
        this.cantDescargas = cantDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                ", titulo='" + titulo + '\'' +
                ", lenguaje='" + lenguaje + '\'' +
                ", cantDescargas=" + cantDescargas +
                '}';
    }


}
