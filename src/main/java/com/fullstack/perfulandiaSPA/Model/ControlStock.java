package com.fullstack.perfulandiaSPA.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Genera un constructor con todos los campos.
@NoArgsConstructor // Genera un constructor con todos los campos.

public class ControlStock {
    private int id;
    private Perfumes perfume;
    private int stockActual;
    private String fechaActualizacion;
    
}
