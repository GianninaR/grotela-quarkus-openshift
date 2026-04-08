package com.example.favorito;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "favorito",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tipo", "referenciaId"})
)
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String tipo;

    @NotBlank
    public String referenciaId;

    @NotBlank
    public String descripcion;

    public LocalDateTime fechaRegistro;
}
