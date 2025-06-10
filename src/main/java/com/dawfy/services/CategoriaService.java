package com.dawfy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Categoria;
import com.dawfy.persistence.repositories.CategoriaCrudRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaCrudRepository categoriaCrudRepository;

    public boolean existsByNombre(String nombre) {
        return this.categoriaCrudRepository.existsByNombre(nombre);
    }

    public List<Categoria> getCategoriasByNombre(String nombre) {
        return this.categoriaCrudRepository.findByNombre(nombre);
    }

    public Categoria createCategoria(Categoria categoria) {
        if (this.existsByNombre(categoria.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoria con el nombre: " + categoria.getNombre());
        }
        return this.categoriaCrudRepository.save(categoria);
    }

}
