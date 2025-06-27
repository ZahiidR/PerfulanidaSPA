package com.fullstack.perfulandiaSPA.Controller;

import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.fullstack.perfulandiaSPA.Assemblers.perfumeModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;

import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@RestController
@RequestMapping("http://192.168.119.154:8080/api/v2/perfumes")
@Tag(name = "Perfume", description = "Operaciones sobre el catalogo de Perfumes")
public class perfumeControllerV2 {

    @Autowired
    private perfumeService perfumeserv;

    
    @Autowired
    private perfumeModelAssembler assembler;
    @Operation(summary = "Desplegar el catalogo de los producto", description = "Despliega una lista de todos los perfumes disponibles")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Perfumes>> listarPerfumes() {
        // Recoge la lista de perfumes y la convierte a EntityModel usando el assembler
        List<EntityModel<Perfumes>> p = perfumeserv.getPerfumes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        // Aquí se genera un enlace al método listarPerfumes() usando methodOn
        return CollectionModel.of(p, 
            linkTo(methodOn(perfumeControllerV2.class).listarPerfumes()).withSelfRel());
    }

    @Operation(summary = "Agregar un perfume al catalogo de los productos", description = "Agrega un nuevo perfume al catalogo")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Perfumes>> agregarPerfume(@RequestBody Perfumes perfume) {
        Perfumes crear = perfumeserv.savePerfume(perfume);
        return ResponseEntity.created(
                linkTo(methodOn(perfumeControllerV2.class).buscarPerfumes(crear.getId())).toUri())
                .body(assembler.toModel(crear));
    }

    @Operation(summary = "Buscar un perfume por ID", description = "Busca un perfume especifico por su ID")
    @GetMapping("/{id}")
    public Perfumes buscarPerfumes(@PathVariable int id) {
        return perfumeserv.getPerfumeId(id);
    }

    @Operation(summary = "Actualizar la informacion de un producto", description = "Actualiza los detalles de un perfume existente")
    @PutMapping("/{id}")
    public Perfumes actualizaPerfumes(@PathVariable int id, @RequestBody Perfumes perfume) {
        return perfumeserv.updatePerfume(perfume);
    }

    @Operation(summary = "Eliminar un producto del catalogo", description = "Elimina un perfume del catalogo por su ID")
    @DeleteMapping("/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        return perfumeserv.deletePerfume(id);
    }
   
    @Operation(summary = "Contar el total de los perfumes", description = "Devuelve el numero total de perfumes en la lista")
    @GetMapping("/total")
    public int totalPerfumesv2() {
        return perfumeserv.totalPerfumesv2();
    }
}