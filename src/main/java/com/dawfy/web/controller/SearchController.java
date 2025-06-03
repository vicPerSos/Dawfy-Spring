package com.dawfy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.domain.dto.BusquedaDto;
import com.dawfy.services.SpotifyService;
import com.dawfy.services.Mappers.SearchService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchSetvice;

    public ResponseEntity<BusquedaDto> normalFinder(String query) {
        BusquedaDto res = this.searchSetvice.busqueda(query);
        return ResponseEntity.ok(res);
    }

}
