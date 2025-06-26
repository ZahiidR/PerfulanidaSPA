package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("http://10.155.64.162:8080/api/v2/carrito")
//@Tag se usa para agrupar y etiquetar los controladores dentro de la documentacion
@Tag(name = "Carrito de Compras", description = "Operaciones sobre el carrito de compras")
public class carritoControllerV2 {
    private final List<Perfumes> carrito = new ArrayList<>();

    @Autowired
    private perfumeService perfumeService;
    //Agregar un perfume al carrito
    @Operation(summary = "Agregar un producto al carrito de compras", description = "Añadir un perfume al carrito de compras por su ID")
    @PostMapping("/agregar/{id}")
    public String agregarPerfume(@PathVariable int id) {
        Perfumes perfumes = perfumeService.getPerfumeId(id);
        if (perfumes != null) {
            carrito.add(perfumes);
            return "Perfume agregado al carrito: " + perfumes.getNombrePerfume();
        }
        return "Perfume no encontrado";
    }
    //Ver el carrito
    @Operation(summary = "Mostrar todos los productos del carrito de compras", description = "Muestra todos los perfumes del carrito")
    @GetMapping
    public List<Perfumes> verCarrito() {
        return carrito;
    }
    //Eliminar un perfume del carrito
    @Operation(summary = "Eliminar un producto del carrito de compras", description = "Elimina un perfume del carrito de compras por su ID")
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? "Perfume eliminado del carrito" : "Perfume no estaba en el carrito";
    }
    //Vaciar el carrito
    @Operation(summary = "Vaciar el carrito de compras", description = "Elimina todos los perfumes del carrito de compras")
    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "Carrito vaciado";
    }
    //Contar los perfumes en el carrito
    @Operation(summary = "Contar los productos del carrito de compras", description = "Devuelve el total de los perfumes del carrito de compras")
    @GetMapping("/total")
    public int totalPerfumesCarrito() {
        return carrito.size();
    }
}


