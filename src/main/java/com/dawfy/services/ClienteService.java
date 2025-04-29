package com.dawfy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Cliente;
import com.dawfy.persistence.repositories.ClienteCrudRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteCrudRepository clienteCrudRepository;

    public List<Cliente> getAllClientes() {
        return (List<Cliente>) this.clienteCrudRepository.findAll();
    }

    public Optional<Cliente> getClienteById(int id) {
        return this.clienteCrudRepository.findById(id);
    }

    public void saveCliente(Cliente cliente) {
        this.clienteCrudRepository.save(cliente);
    }

    public void updateCliente(int id, Cliente cliente) {
        Cliente clienteExistente = this.clienteCrudRepository.findById(id).get();

        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setCorreo(cliente.getCorreo());
        clienteExistente.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteExistente.setPais(cliente.getPais());
        clienteExistente.setFoto(cliente.getFoto());
        clienteExistente.setPassword(cliente.getPassword());
        this.clienteCrudRepository.save(clienteExistente);
    }

    public boolean existsCliente(int id) {
        return this.clienteCrudRepository.existsById(id);
    }

    public boolean deleteCliente(int id) {
        if (!this.clienteCrudRepository.existsById(id)) {
            return false;
        }
        this.clienteCrudRepository.deleteById(id);
        return true;
    }

    public List<Cliente> clientePorPais(String nombre) {
        return this.clienteCrudRepository.findByPais(nombre);
    }

    public String paisDeCliente(int idCliente) {
        try {
            Optional<Cliente> cliente = this.clienteCrudRepository.findById(idCliente);
            if (cliente.isEmpty()) {
                throw new Exception("No existe el cliente");
            }
            return cliente.get().getPais().getNombre();

        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Cliente> clientePorCorreo(String correo) {
        return this.clienteCrudRepository.findByCorreo(correo);
    }

    public List<Cliente> clientePorCumle(LocalDate fechaNacimiento) {
        return this.clienteCrudRepository.findByFechaNacimiento(fechaNacimiento);
    }

    public String correoCliente(int id) {
        return this.clienteCrudRepository.findById(id).get().getCorreo();
    }

    public LocalDate fechaCliente(int id) {
        return this.clienteCrudRepository.findById(id).get().getFechaNacimiento();
    }

    public List<Cliente> clientePorNombre(String nombre) {
        return this.clienteCrudRepository.findByNombreStartingWithIgnoreCase(nombre);
    }
}