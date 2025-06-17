package com.fullstack.perfulandiaSPA.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
     private int id;
    private String descripcion;
    private String usuario;
    private String estado;
    
}
