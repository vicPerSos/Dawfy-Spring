package com.dawfy.persistence.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genero {

    @EmbeddedId
    private GeneroId id;

    @ManyToOne
    @JoinColumn(name = "artista", insertable = false, updatable = false)
    private Artista artista;

    @ManyToOne
    @JoinColumn(name = "categoria", insertable = false, updatable = false)
    private Categoria categoria;

    public Genero(Artista artista, Categoria categoria) {
        this.id = new GeneroId();
        this.id.setArtista(artista.getId());
        this.id.setCategoria(categoria.getId());
        this.artista = artista;
        this.categoria = categoria;
    }

}
