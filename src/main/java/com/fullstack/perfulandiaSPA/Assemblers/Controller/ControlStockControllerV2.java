package com.fullstack.perfulandiaSPA.Assemblers.Controller;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Service.ControlStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.fullstack.perfulandiaSPA.Assemblers.controlStockModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;

import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@RestController
@RequestMapping("http://192.168.119.154:8080/api/v2/stock")
@Tag(name = "Stock", description = "Operaciones sobre el control de stock")
public class ControlStockControllerV2 {
     @Autowired
    private ControlStockService controlserv;

    @Autowired
    private controlStockModelAssembler assembler;

    @Operation(summary = "Desplegar el catalogo del stock", 
    description = "Despliega una lista del stock disponibles")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ControlStock>> listarControlStocks() {
            List<EntityModel<ControlStock>> controlStock = controlserv.getControlStocks().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(controlStock, 
            linkTo(methodOn(ControlStockControllerV2.class).listarControlStocks()).withSelfRel());
    }

    @Operation(summary = "Agregar stock",
    description = "Agrega stock de los perfumes")
    @PostMapping(value = "/agregar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ControlStock>> agregarControlStock(@RequestBody ControlStock controlStock) {
        ControlStock crear = controlserv.saveControlStock(controlStock);
        return ResponseEntity.created(linkTo(methodOn(ControlStockControllerV2.class).buscarControlStock(crear.getId())).toUri()).body(assembler.toModel(crear));
    }

    @Operation(summary = "Buscar stock por ID",
    description = "Busca stock especifico por ID")
    @GetMapping( value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ControlStock> buscarControlStock(@PathVariable int id){
        ControlStock controlStock = controlserv.getControlStocks(id);
        return assembler.toModel(controlStock);
    }

    @Operation(summary = "Actualizar la informacion de stock", 
    description = "Actualiza los detalles del stock")    
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ControlStock>> actualizaControlStock(@PathVariable int id, @RequestBody ControlStock controlStock) {
        controlStock.setId(id);
        ControlStock actualizado = controlserv.updateControlStock(controlStock);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    
    }

    @Operation(summary = "Eliminar stock por ID", 
    description = "Elimina stock especifico por ID")    
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarControl(@PathVariable int id) {
        controlserv.deleteControl(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Contar el total de stock", 
    description = "Devuelve la cantidad disponible de los productos")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public int totalPerfumesv2(){
        return controlserv.totalControlesV2();
    }
}

