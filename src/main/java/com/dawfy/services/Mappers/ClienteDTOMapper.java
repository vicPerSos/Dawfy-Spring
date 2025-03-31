package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Cliente;
import com.dawfy.services.DTOs.ClienteDTO;

public class ClienteDTOMapper {
    public static ClienteDTO mapper(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setCorreo(cliente.getCorreo());
        clienteDto.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteDto.setPais(cliente.getPais().getNombre());
        clienteDto.setFoto(cliente.getFoto());

        return clienteDto;
    }

}
