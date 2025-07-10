package com.fullstack.perfulandiaSPA.Assemblers.Controller;
import com.fullstack.perfulandiaSPA.Assemblers.carritoModelAssembler;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.*;
import java.util.stream.Collectors;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("http://http://192.168.119.154:8080/api/v2/carrito")
//@Tag se usa para agrupar y etiquetar los controladores dentro de la documentacion
@Tag(name = "Carrito de Compras", description = "Operaciones sobre el carrito de compras")
public class carritoControllerV2 {
    private final List<Perfumes> carrito = new ArrayList<>();
    @Autowired
    private perfumeService perfumeService;
    @Autowired
    private carritoModelAssembler assembler;
    //Agregar un perfume al carrito
    @Operation(summary = "Agregar un producto al carrito de compras", description = "Añadir un perfume al carrito de compras por su ID")
    @PostMapping(value = "/agregar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> agregarPerfume(@PathVariable int id) {
        Perfumes perfumes = perfumeService.getPerfumeId(id);
        if (perfumes != null) {
            carrito.add(perfumes);
            return ResponseEntity.ok("Perfume agregado al carrito: " + perfumes.getNombrePerfume());
        }
        return ResponseEntity.badRequest().body("Perfume no encontrado");
    }
    //Ver el carrito
    @Operation(summary = "Mostrar todos los productos del carrito de compras", description = "Muestra todos los perfumes del carrito")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Perfumes>> verCarrito() {
        List<EntityModel<Perfumes>> perfumes = carrito.stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(perfumes,linkTo(methodOn(carritoControllerV2.class).verCarrito()).withSelfRel());
    }
    //Eliminar un perfume del carrito
    @Operation(summary = "Eliminar un producto del carrito de compras", description = "Elimina un perfume del carrito de compras por su ID")
    @DeleteMapping(value = "/eliminar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? ResponseEntity.ok("Perfume eliminado del carrito") : ResponseEntity.ok("Perfume no estaba en el carrito");
    }
    //Vaciar el carrito
    @Operation(summary = "Vaciar el carrito de compras", description = "Elimina todos los perfumes del carrito de compras")
    @DeleteMapping(value = "/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> vaciarCarrito() {
        carrito.clear();
        return ResponseEntity.ok("Carrito vaciado");
    }
    //Contar los perfumes en el carrito
    @Operation(summary = "Contar los productos del carrito de compras", description = "Devuelve el total de los perfumes del carrito de compras")
    @GetMapping("/total")
    public int totalPerfumesCarrito() {
        return carrito.size();
    }
}


