package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import com.fullstack.perfulandiaSPA.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.fullstack.perfulandiaSPA.Assemblers.reporteModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;

import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@RestController
@RequestMapping("http://192.168.119.154:8080/api/v2/reporte")
@Tag(name = "Reporte", description = "Operaciones sobre el reporte de Perfumes")
public class ReporteControllerV2 {
     @Autowired
    private ReporteService reporteService;

    @Autowired
    private reporteModelAssembler assembler;

    @Operation(summary = "Desplegar la lista de los reportes", 
    description = "Despliega una lista de todos los reportes disponibles")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reporte>> listarReporte() {
        //Obtener la lista de libros y la convertiremos a EntityModel usando el assembler
        List<EntityModel<Reporte>> reporte = reporteService.getReportes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(reporte, 
            linkTo(methodOn(ReporteControllerV2.class).listarReporte()).withSelfRel());
    }

    @Operation(summary = "Agregar nuevo reporte", 
    description = "Agrega nuevo reporte de perfumes")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reporte>> agregarReporte(@RequestBody Reporte r) {
        Reporte crear = reporteService.saveReporte(r);
        return ResponseEntity.created(linkTo(methodOn(ReporteControllerV2.class).buscarReporte(crear.getId())).toUri()).body(assembler.toModel(crear));
    }

    @Operation(summary = "Buscar reportes", 
    description = "Busca reportes especificos por su ID")
    @GetMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reporte> buscarReporte(@PathVariable int id){
        Reporte r = reporteService.getReporte(id);
        return assembler.toModel(r);
    }

    @Operation(summary = "Actualizar informacion del reporte", 
    description = "Actualiza los detalles del reporte")
    @PutMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reporte>> actualizarReporte(@PathVariable int id, @RequestBody Reporte r) {
        r.setId(id);
        Reporte actualizado = reporteService.deleteReporte(r);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @Operation(summary = "Eliminar reporte", 
    description = "Elimina reporte especifico por su ID")
    @DeleteMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarReporte(@PathVariable int id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Contar reportes generados", 
    description = "Devuelve la cantidad de reportes")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public int totalReporteV2() {
        return reporteService.totalReporteV2();
    }

    
}
