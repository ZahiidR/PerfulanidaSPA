package com.fullstack.perfulandiaSPA.Controller;

import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fullstack.perfulandiaSPA.Assemblers.perfumeModelAssembler;
import com.fullstack.perfulandiaSPA.Controller.perfumeController;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/perfumes")
public class perfumeControllerV2 {

    @Autowired
    private perfumeService perfumeserv;

    @Autowired
    private perfumeModelAssembler assembler;

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

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Perfumes>> agregarPerfume(@RequestBody Perfumes perfume) {
        Perfumes crear = perfumeserv.savePerfume(perfume);
        return ResponseEntity.created(
                linkTo(methodOn(perfumeControllerV2.class).buscarPerfumes(crear.getId())).toUri())
                .body(assembler.toModel(crear));
    }

    @GetMapping("/{id}")
    public Perfumes buscarPerfumes(@PathVariable int id) {
        return perfumeserv.getPerfumeId(id);
    }

    @PutMapping("/{id}")
    public Perfumes actualizaPerfumes(@PathVariable int id, @RequestBody Perfumes perfume) {
        return perfumeserv.updatePerfume(perfume);
    }

    @DeleteMapping("/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        return perfumeserv.deletePerfume(id);
    }
   
    @GetMapping("/total")
    public int totalPerfumesv2() {
        return perfumeserv.totalPerfumesv2();
    }
}