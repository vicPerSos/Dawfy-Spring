package com.dawfy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Cancion;
import com.dawfy.persistence.repositories.CancionCrudRepository;

@Service
public class CancionService {
    @Autowired
    private CancionCrudRepository cancionRepository;

    public List<Cancion> getAllCanciones() {
        return (List<Cancion>) this.cancionRepository.findAllWithCategoriaAndColaboradores();
    }

    public List<Cancion> findByNombre(String nombre) {
        return cancionRepository.findByNombreWithCategoriaAndColaboradores(nombre);
    }

    public Optional<Cancion> findById(int id) {
        return cancionRepository.findByIdWithCategoriaAndColaboradores(id);
    }

    public Cancion save(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    @SuppressWarnings("null")
    public Cancion update(Cancion cancion, int id) {
        Cancion cancionBD = cancionRepository.findById(id).orElse(null);
        if (cancionBD != null) {
            cancionBD.setNombre(cancion.getNombre());
            cancionBD.setDuracion(cancion.getDuracion());   
            cancionBD.setAlbum(cancion.getAlbum());
            cancionBD.setUrl(cancion.getUrl());
        }
        return cancionRepository.save(cancionBD);
    }

    public void delete(Cancion cancion) {
        cancionRepository.delete(cancion);
    }

}
