package com.aluracursos.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;

    private Double descargas;

    //private List<DatosAutor> autores;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Idiomas> idiomas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Autores> autores;

    public Libros(){}

    public Libros(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.descargas = datosLibros.descargas();
        // Crear lista de idiomas a partir de los datos
        this.idiomas = datosLibros.idiomas().stream()
                .map(Idiomas::new) // Crear objetos Idiomas
                .collect(Collectors.toList());
        // Establecer la relación bidireccional
        this.idiomas.forEach(idioma -> idioma.setLibros(this));

        this.autores = datosLibros.autores().stream()
                .map(Autores::new) // Crear objetos Idiomas
                .collect(Collectors.toList());
        // Establecer la relación bidireccional
        this.autores.forEach(autor -> autor.setLibro(this));
    }
    //GETTERS AND SETTERS
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public List<Idiomas> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idiomas> idiomas) {
        idiomas.forEach(i -> i.setLibros(this));
        this.idiomas = idiomas;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        autores.forEach(i -> i.setLibro(this));
        this.autores = autores;
    }



}
