package com.dawfy.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String correo;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "pais")
    private Pais pais;

    @Column(columnDefinition = "TEXT")
    private String foto;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Column(name = "cuenta_expirada")
    private boolean cuentaExpirada;
    @Column(name = "cuenta_bloqueada")
    private boolean cuentaBloqueada;
    @Column(name = "credencial_expirada")
    private boolean credencialExpirada;
    @Column(name = "habilitada")
    private boolean habilitada;
    @Column(name = "roll")
    private String roll;
}
