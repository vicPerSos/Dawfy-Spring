package com.dawfy.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "username", columnDefinition = "TEXT")
    private String username;

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
