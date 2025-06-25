package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/carrito")
public class carritoControllerV2 {
    private final List<Perfumes> carrito = new ArrayList<>();

    @Autowired
    private perfumeService perfumeService;
    //Agregar un perfume al carrito
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
    @GetMapping
    public List<Perfumes> verCarrito() {
        return carrito;
    }
    //Eliminar un perfume del carrito
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? "Perfume eliminado del carrito" : "Perfume no estaba en el carrito";
    }
    //Vaciar el carrito
    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "Carrito vaciado";
    }
    //Contar los perfumes en el carrito
    @GetMapping("/total")
    public int totalPerfumesCarrito() {
        return carrito.size();
    }
}


