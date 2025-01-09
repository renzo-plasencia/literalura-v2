package com.aluracursos.literalura.services;

import com.aluracursos.literalura.models.Libros;
import com.aluracursos.literalura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;

    public List<Libros> obtenerTodosLibros(){
        return repository.findAll();
    }
}
