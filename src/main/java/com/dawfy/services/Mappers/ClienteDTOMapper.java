package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Cliente;
import com.dawfy.services.DTOs.ClienteDTO;

public class ClienteDTOMapper {
    public static ClienteDTO mapper(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setCorreo(cliente.getCorreo());
        clienteDto.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteDto.setPais(cliente.getPais().getNombre());

        return clienteDto;
    }

}
