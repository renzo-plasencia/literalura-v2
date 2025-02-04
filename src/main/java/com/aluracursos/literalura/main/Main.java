package com.aluracursos.literalura.main;

import com.aluracursos.literalura.models.*;
import com.aluracursos.literalura.repositorio.LibroRepository;
import com.aluracursos.literalura.services.ConsumoAPI;
import com.aluracursos.literalura.services.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    //Scanner
    private Scanner s = new Scanner(System.in);
    //Endpoint
    private final String endpoint = "https://gutendex.com/books/?search=";
    //Consumo API
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    //Convertidor de datos (Json -> datosTotales)
    private ConvierteDatos conversor = new ConvierteDatos();

    //private List<DatosLibros> librosList = new ArrayList<>();
    private String libro;

    private LibroRepository repositorio;
    //private List<Libros> libros;
    Optional<Libros> libroBuscado;

    public Main(LibroRepository repository){
        this.repositorio = repository;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por Título
                    2 - Lista de Libros Registrados (BBDD)
                    3 - Lista de Autores Registrados (BBDD)
                    4 - Lista de Autores vivos en un año (BBDD)
                    5 - Lista de Libros por idioma (BBDD)
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = s.nextInt();
            s.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    obtenerListaLibros();
                    break;
                case 3:
                    //System.out.println("Seleccionaste opción "+opcion);
                    imprimirAutoresYLecturas();
                    break;
                case 4:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 5:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosTotales getDatosLibros(){
        System.out.println("Ingresa el nombre del libro a buscar: ");
        libro = s.nextLine().toLowerCase(); //Nombre del libro
        var json = consumoAPI.obtenerDatos(endpoint+libro.replace(" ","+"));
        DatosTotales datos = conversor.obtenerDatos(json, DatosTotales.class);
        return datos;
    }

    private void buscarLibroTitulo(){
        DatosTotales datos = getDatosLibros();
        //Filtrar primer libro encontrado
        Optional<DatosLibros> libroBusqueda = obtenerPrimerLibro(datos,libro);
        //Como devuelve un optional, si está presente. Se transforma a libro, se imprime y guarda como libro.
        if (libroBusqueda.isPresent()) {
            DatosLibros libroA = libroBusqueda.get(); //Extrae el valor

            boolean busquedaBBDD = estadoLibroBBDD(libroA.titulo());
            Libros libroB = new Libros(libroA);

            String autores = libroA.autores().stream()
                    .map(DatosAutor::nombreAutor)
                    .collect(Collectors.joining(", "));

            if (!busquedaBBDD) {
                System.out.println("Título: " + libroA.titulo());
                System.out.println("Autores: " + autores);
                System.out.println("Idioma: " + String.join(", ", libroA.idiomas()));
                System.out.println("Descargas: " + libroA.descargas());
                repositorio.save(libroB);
            } else {
                System.out.println("Título: " + libroA.titulo());
                System.out.println("Autores: " + autores);
                System.out.println("Idioma: " + String.join(", ", libroA.idiomas()));
                System.out.println("Descargas: " + libroA.descargas());
            }
        }
    }

    private boolean estadoLibroBBDD(String nombreLibro){
        libroBuscado = repositorio.findByTituloContainingIgnoreCase(nombreLibro);
        if (libroBuscado.isPresent()){
            System.out.println("Se encontró el libro: "+libroBuscado.get().getTitulo());
            return true;
        } else {
            System.out.println("Libro no encontrada en la BBDD. Guardando...");
            return false;
        }
    }

    //Trae el primer libro
    private Optional<DatosLibros> obtenerPrimerLibro(DatosTotales datos, String libro){
        return datos.librosList().stream()
                .filter(l -> l.titulo().toUpperCase().contains(libro.toUpperCase()))
                .findFirst();
    }

    private void obtenerListaLibros(){
        var busquedas = repositorio.findAll();
        busquedas.stream()
                .forEach(libro -> {
                    String autores = libro.getAutores().stream()
                            .map(Autores::getNombreAutor)
                            .collect(Collectors.joining(", "));

                    String idiomas = libro.getIdiomas().stream()
                            .map(idioma -> idioma.getIdioma())
                            .collect(Collectors.joining(", "));

                            System.out.println("----------------------");
                            System.out.printf(
                                    "Título: %s\nAutores: %s\nDescargas: %.0f\nIdiomas: %s\n",
                                    libro.getTitulo(),
                                    autores,
                                    libro.getDescargas(),
                                    idiomas
                            );
                        }
                    );

    }

    private void imprimirAutoresYLecturas() {
        var libros = repositorio.findAllWithAutoresAndIdiomas();

        libros.stream()
                .flatMap(libro -> libro.getAutores().stream()) // Explorar cada autor de cada libro
                .distinct() // Evitar duplicados si un autor tiene varios libros
                .forEach(autor -> {
                    // Imprimir información del autor
                    System.out.println("Autor: " + autor.getNombreAutor());
                    System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.getFechaMuerte());

                    // Obtener y formatear los títulos de los libros del autor
                    String librosDelAutor = autor.getLibro().getTitulo();

                    System.out.println("Libros: [" + librosDelAutor + "]");
                    System.out.println("----------------------");
                });
    }
}
