package com.fullstack.perfulandiaSPA.Controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

@RestController
@RequestMapping("/api/v1/carrito")
public class carritoController {
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

    @PostMapping
public String agregarPerfumeDesdeFront(@RequestBody Perfumes perfume) {
    if (perfume != null) {
        carrito.add(perfume);
        return "Perfume agregado desde frontend: " + perfume.getNombrePerfume();
    }
    return "No se pudo agregar el perfume";
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
    public int obtenerTotal() {
        return carrito.size();
    }
}


