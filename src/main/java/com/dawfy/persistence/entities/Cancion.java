package com.dawfy.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cancion")
@Getter
@Setter
@NoArgsConstructor
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcancion")
    private int id;
    private String nombre;
    private int duracion;

    @Column(name = "id_album")
    private int idAlbum;

    @ManyToOne
    @JoinColumn(name = "id_album", referencedColumnName = "idalbum", insertable = false, updatable = false)
    @JsonIgnore
    private Album album;
    private String imagen;
    private String url;

    @ManyToMany
    @JoinTable(
        name = "cancioncategoria",
        joinColumns = @JoinColumn(name = "cancion_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(
        name = "colaboracion",
        joinColumns = @JoinColumn(name = "cancion_id"),
        inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private List<Artista> colaboradores;

}
