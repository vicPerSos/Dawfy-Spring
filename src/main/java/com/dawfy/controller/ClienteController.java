package com.dawfy.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.controller.requestBody.cliente.ClienteRequestBodyPOST;
import com.dawfy.controller.requestBody.cliente.ClienteRequestBodyPUT;
import com.dawfy.persistence.entities.Cliente;
import com.dawfy.services.ClienteService;
import com.dawfy.services.PaisService;
import com.dawfy.services.DTOs.ClienteDTO;
import com.dawfy.services.Mappers.ClienteDTOMapper;

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

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteDTO>> getClientesByNombre(@PathVariable String nombre) {
        List<Cliente> clientes = this.clienteService.clientePorNombre(nombre);
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesDTO.add(ClienteDTOMapper.mapper(cliente));
        }
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/fecha/{idCliente}")
    public ResponseEntity<String> fechaNacimiento(@PathVariable int idCliente) {
        Optional<Cliente> cliente = this.clienteService.getClienteById(idCliente);
        if (cliente.isPresent()) {
            if (cliente.get().getFechaNacimiento() != null) {
                return ResponseEntity.ok(cliente.get().getFechaNacimiento().toString());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cumple/{dia}")
    public ResponseEntity<String> cumple(@PathVariable String dia) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(dia, formatter);
            List<Cliente> clientes = this.clienteService.clientePorCumle(fecha);
            List<ClienteDTO> clientesDTO = new ArrayList<>();
            for (Cliente cliente : clientes) {
                clientesDTO.add(ClienteDTOMapper.mapper(cliente));
            }
            return ResponseEntity.ok(clientesDTO.toString());
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<ClienteDTO>> clientePorPais(@PathVariable String pais) {
        if (pais.length() != 2) {
            return ResponseEntity.badRequest().build();
        }
        List<Cliente> clientes = this.clienteService.clientePorPais(pais.toUpperCase());
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesDTO.add(ClienteDTOMapper.mapper(cliente));
        }
        return ResponseEntity.ok(clientesDTO);

    }

    @GetMapping("/paisDe/{idCliente}")
    public ResponseEntity<String> paisDe(@PathVariable int idCliente) {
        String respuesta = this.clienteService.paisDeCliente(idCliente);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/correo/{idCliente}")
    public ResponseEntity<String> getCorreo(@PathVariable int idCliente) {
        Optional<Cliente> cliente = this.clienteService.getClienteById(idCliente);
        if (cliente.isPresent()) {
            if (cliente.get().getCorreo() != null) {
                return ResponseEntity.ok(cliente.get().getCorreo());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/correoDe/{correo}")
    public ResponseEntity<ClienteDTO> correoDe(@PathVariable String correo) {
        Optional<Cliente> cliente = this.clienteService.clientePorCorreo(correo);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClienteDTOMapper.mapper(cliente.get()));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteRequestBodyPOST cliente) {
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre(cliente.getNombre());
        clienteNuevo.setCorreo(cliente.getCorreo());
        clienteNuevo.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteNuevo.setPais(this.paisService.findById(cliente.getPais()));
        if (cliente.getFoto() != null) {
            clienteNuevo.setFoto(cliente.getFoto());
        }
        this.clienteService.saveCliente(clienteNuevo);
        return ResponseEntity.ok(ClienteDTOMapper.mapper(clienteNuevo));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable int id,
            @RequestBody ClienteRequestBodyPUT cliente) {
        if (!this.clienteService.existsCliente(id)) {
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
        if (cliente.getFoto() != null) {
            clienteNuevo.setFoto(cliente.getFoto());
        }
        this.clienteService.updateCliente(clienteNuevo);
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