package com.fullstack.perfulandiaSPA.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //Genera getters, setters, toString, equals, hashCode y un constructor con los campos requeridos.
@AllArgsConstructor // Genera un constructor con todos los campos.
@NoArgsConstructor // Genera un constructor con todos los campos.

public class Perfumes {
    private int id;
    private String nombrePerfume;
    private String marca;
    private int precio;
    private int cantidadMl;
    private String descripcion;
    private int stock;
}


