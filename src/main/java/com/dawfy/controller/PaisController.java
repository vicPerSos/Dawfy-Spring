package com.dawfy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.dawfy.persistence.entities.Pais;
import com.dawfy.services.PaisService;

@RestController
@RequestMapping("/pais")
public class PaisController {
    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<Pais>> paises() {
        return ResponseEntity.ok(this.paisService.getAll());
    }

    @GetMapping("/{codigoIso}")
    public ResponseEntity<Pais> pais(@PathVariable String codigoIso) {
        Pais pais = this.paisService.findById(codigoIso);
        if (this.paisService.exists(codigoIso)) {
            return ResponseEntity.ok(pais);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{codigoIso}")
    public ResponseEntity<Pais> update(@PathVariable String codigoIso, @RequestBody Pais pais) {
        if (this.paisService.exists(codigoIso)) {
            return ResponseEntity.ok(this.paisService.update(codigoIso, pais));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigoIso}")
    public ResponseEntity<Void> delete(@PathVariable String codigoIso) {
        if (this.paisService.delete(codigoIso)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Pais> create(@RequestBody Pais pais) {
        return ResponseEntity.ok(this.paisService.save(pais));
    }
}
