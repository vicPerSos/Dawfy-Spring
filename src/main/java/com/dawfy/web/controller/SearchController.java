package com.dawfy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.domain.dto.BusquedaDto;
import com.dawfy.services.Mappers.SearchService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchSetvice;

    @Operation(summary = "Buscar algo por nombre")
    @GetMapping("/{query}")
    public ResponseEntity<BusquedaDto> normalFinder(@PathVariable String query) {
        BusquedaDto res = this.searchSetvice.busqueda(query);
        return ResponseEntity.ok(res);
    }

}
