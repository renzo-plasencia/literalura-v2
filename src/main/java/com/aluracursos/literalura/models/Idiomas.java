package com.aluracursos.literalura.models;

import jakarta.persistence.*;

@Entity
public class Idiomas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String idioma;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libros libro;

    public Idiomas(){}

    public Idiomas(String idioma){
        this.idioma = idioma;
    }

    //GETTERS AND SETTERS

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Libros getLibros() {
        return libro;
    }

    public void setLibros(Libros libro) {
        this.libro = libro;
    }
}
