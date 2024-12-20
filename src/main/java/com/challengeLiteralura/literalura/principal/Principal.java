package com.challengeLiteralura.literalura.principal;

import com.challengeLiteralura.literalura.model.*;
import com.challengeLiteralura.literalura.repository.AutorRepository;
import com.challengeLiteralura.literalura.repository.LibroRepository;
import com.challengeLiteralura.literalura.service.ConsumoAPI;
import com.challengeLiteralura.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Elija una opción:
                    1.Buscar libro por titulo
                    2.Listar libros registrados
                    3.Listar autores registrados
                    4.Listar autores vivos por determinado año
                    5.Listar libros por idioma
                    0.Salir
                    """;

            System.out.println(menu);
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Hasta luego");
                default -> System.out.println("Opción no válida");
            }
        }


    }

    private DatosResultado getLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro a buscar");
        String nombrelibro = scanner.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombrelibro.replace(" ", "+"));
        System.out.println(json);
        DatosResultado datosResultado = conversor.obtenerDatos(json, DatosResultado.class);
        return datosResultado;
    }

    private void buscarLibroPorTitulo() {
        DatosResultado datosResultado = getLibroPorTitulo();
        if (datosResultado != null) {
            for (DatosLibro datosLibro : datosResultado.resultados()) {
                // Buscar si el libro ya existe por título (o por otro criterio único)
                Optional<Libro> libroExistente = Optional.ofNullable(libroRepository.findByTitulo(datosLibro.titulo()));

                if (libroExistente.isPresent()) {
                    System.out.println("El libro ya existe en la base de datos");
                    break;
                } else {
                    // Crear un nuevo libro
                    Libro libro = new Libro(datosLibro);

                    // Recorrer lista de autores y asociarlos al libro
                    for (DatosAutor datosAutor : datosLibro.autor()) {
                        if (datosAutor != null) { // Verificar que el autor no sea null
                            // Buscar si el autor ya existe
                            Optional<Autor> autorExistente = Optional.ofNullable(autorRepository.findByNombre(datosAutor.nombre()));

                            Autor autor;
                            if (autorExistente.isPresent()) {
                                autor = autorExistente.get(); // Usar el autor existente
                            } else {
                                // Crear un nuevo autor
                                autor = new Autor(datosAutor);
                                // Guardar el autor en la base de datos
                                autorRepository.save(autor);
                            }

                            // Asociar autor al libro
                            if (libro.getAutores() == null) {
                                libro.setAutores(new HashSet<>());
                            }
                            libro.getAutores().add(autor);
                        }
                    }

                    // Guardar el libro en la base de datos
                    libroRepository.save(libro);
                    System.out.println("Libro guardado: " + libro);
                }
            }
        }
    }

    private void listarLibros() {
        System.out.println("Listado de libros");
        libros = libroRepository.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);

    }

    private void listarAutores() {
        System.out.println("Listado de autores");
        autores = autorRepository.findAll();
        autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Escribe el año a buscar");
        int ano = Integer.parseInt(scanner.nextLine());
        autores = autorRepository.findAutoresVivosPorAno(ano);
        autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        String idiomas = """
                Escribe el idioma a buscar:
                es
                en
                fr
                de
                it
                pt
                ru
                """;
        System.out.println(idiomas);
        String idioma = scanner.nextLine();
        libros = libroRepository.findByLenguaje(Lenguaje.fromString(idioma));
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
    }


}
