package com.dawfy.controller;

import java.util.ArrayList;
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

import com.dawfy.controller.requestBody.ClienteRequestBody;
import com.dawfy.persistence.entities.Cliente;
import com.dawfy.services.ClienteService;
import com.dawfy.services.PaisService;
import com.dawfy.services.DTOs.ClienteDTO;
import com.dawfy.services.Mappers.ClienteDTOMapper;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PaisService paisService;

    @GetMapping
    private ResponseEntity<List<ClienteDTO>> getClientes() {
        List<Cliente> clientes = this.clienteService.getAllClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesDTO.add(ClienteDTOMapper.mapper(cliente));
        }
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable int idCliente) {
        Optional<Cliente> result = this.clienteService.getClienteById(idCliente);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClienteDTOMapper.mapper(result.get()));
    }

    // TODO :Probar CRUDS de crear, actualizar y eliminar
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable int id, @RequestBody ClienteRequestBody cliente) {
        if (!this.clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (cliente.getId() != id) {
            return ResponseEntity.badRequest().build();
        }
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setId(cliente.getId());
        clienteNuevo.setNombre(cliente.getNombre());
        clienteNuevo.setCorreo(cliente.getCorreo());
        clienteNuevo.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteNuevo.setPais(this.paisService.findById(cliente.getPais()));
        this.clienteService.saveCliente(clienteNuevo);
        return ResponseEntity.ok(ClienteDTOMapper.mapper(clienteNuevo));
    }

    @PostMapping("path")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteRequestBody cliente) {
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre(cliente.getNombre());
        clienteNuevo.setCorreo(cliente.getCorreo());
        clienteNuevo.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteNuevo.setPais(this.paisService.findById(cliente.getPais()));
        this.clienteService.saveCliente(clienteNuevo);
        return ResponseEntity.ok(ClienteDTOMapper.mapper(clienteNuevo));

    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int idCliente) {
        if (this.clienteService.deleteCliente(idCliente)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}