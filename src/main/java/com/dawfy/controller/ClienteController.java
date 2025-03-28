package com.dawfy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.persistence.entities.Cliente;
import com.dawfy.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    private ResponseEntity<List<Cliente>> getClientes() {
        return ResponseEntity.ok(this.clienteService.getAllClientes());

    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Optional<Cliente>> getClienteById(@PathVariable int idCliente) {
        Optional<Cliente> result=this.clienteService.getClienteById(idCliente);
        if(result .isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}
