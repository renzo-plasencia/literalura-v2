package com.aluracursos.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(@JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<DatosAutor> autores,
                          @JsonAlias("languages") List<String> idiomas,
                          @JsonAlias("download_count") Double descargas) {
    @Override
    public String toString() {
        return "\n" + "Título: " + titulo + "\n" +
                "Autores: " + autores + "\n" +
                "Idioma: " + idiomas + "\n" +
                "Descargas: " + descargas + "\n";
    }
}
