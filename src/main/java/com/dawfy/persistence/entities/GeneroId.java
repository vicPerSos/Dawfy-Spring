package com.dawfy.persistence.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class GeneroId {
    private int artista; // Debe coincidir con el tipo de la PK de Artista
    private int categoria;
}
