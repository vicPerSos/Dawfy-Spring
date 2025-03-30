package com.dawfy.services;

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

    public boolean deleteCliente(int id) {
        if (!this.clienteCrudRepository.existsById(id)) {
            return false;
        }
        this.clienteCrudRepository.deleteById(id);
        return true;
    }

    public boolean existsById(int id) {
        return this.clienteCrudRepository.existsById(id);
    }

}
