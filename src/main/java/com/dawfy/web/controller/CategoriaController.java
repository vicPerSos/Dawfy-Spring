package com.dawfy.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dawfy.persistence.entities.Categoria;
import com.dawfy.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<String> getCategorias() {
        List<Categoria> lista = this.categoriaService.getAllCategorias();
        List<String> result = new ArrayList<>();
        for (Categoria categoria : lista) {
            result.add(categoria.getNombre());
        }
        return result;

    }

}
