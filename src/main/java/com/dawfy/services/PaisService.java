package com.dawfy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Pais;
import com.dawfy.persistence.repositories.PaisCrudRepository;

@Service
public class PaisService {
    @Autowired
    private PaisCrudRepository paisRepository;

    public Pais findByNombre(String nombre) {
        return this.paisRepository.findByNombre(nombre).get();
    }

    public boolean exists(String id) {
        return this.paisRepository.existsById(id);
    }

    public List<Pais> getAll() {
        return (List<Pais>) this.paisRepository.findAll();
    }

    public Pais findById(String id) {
        return this.paisRepository.findById(id).get();
    }

    public Pais save(Pais pais) {
        return this.paisRepository.save(pais);
    }

    public Pais update(String id, Pais pais) {
        return this.paisRepository.save(pais);
    }

    public boolean delete(String id) {
        if (this.paisRepository.existsById(id)) {
            this.paisRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
