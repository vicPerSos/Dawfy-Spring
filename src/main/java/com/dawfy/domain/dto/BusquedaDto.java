package com.dawfy.domain.dto;

import java.util.List;

import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.DTOs.ArtistaDTO;
import com.dawfy.services.DTOs.CancionDTO;

import lombok.Data;

@Data
public class BusquedaDto {
    List<ArtistaDTO> artistas;
    List<AlbumDTO> albums;
    List<CancionDTO> canciones;

}
