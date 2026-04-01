package com.example.producto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Producto extends PanacheEntity {

    @Column(nullable = false)
    public String nombre;

    @Column(nullable = false)
    public Double precio;

    public Integer stock;
}
