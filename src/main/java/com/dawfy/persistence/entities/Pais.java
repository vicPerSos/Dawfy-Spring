package com.dawfy.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String codigo_iso;
    @Column(length = 100)
    private String nombre;
}
