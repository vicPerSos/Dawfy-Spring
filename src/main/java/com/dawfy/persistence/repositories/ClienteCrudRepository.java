package com.dawfy.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Cliente;

public interface ClienteCrudRepository extends CrudRepository<Cliente,Integer> {

}
