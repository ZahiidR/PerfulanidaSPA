package com.fullstack.perfulandiaSPA.Model;

import java.util.Optional;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class Usuario { // Está clase se transformará en un tabla en nuestra base de datos
    @Id //generar el campo clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genera un valor por defecto de clave primaria en la base de datos
    private Long id;

    private String nombre;
    private String email;
    private String password;

    // generar codigo para que lo de arriba se vuelva una tabla en la base de datos

    public static Optional<Usuario> map(Object o){ //metodo automatico para enviar el objeto usuario a la base de datos
        throw new UnsupportedOperationException("Unimplemented method 'map");
    }
}
