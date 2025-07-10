package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.ControlStockService;
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

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("http://192.168.119.154:8080/api/v1/stock")
@Tag(name = "Stock", description = "Operaciones sobre el control de stock")
public class ControlStockController {
     @Autowired
    private ControlStockService controlserv;

    @Operation(summary = "Desplegar el catalogo del stock", description = "Despliega una lista del stock disponibles")
    @GetMapping
    public List<ControlStock> listarControlStocks(){
        return controlserv.getControlStocks();
    }

    @Operation(summary = "Agregar stock", description = "Agrega stock de los perfumes")
    @PostMapping("/agregar")
    public ControlStock agregarControlStock(@RequestBody ControlStock controlStock){
        return controlserv.saveControlStock(controlStock);
    }

    @Operation(summary = "Buscar stock por ID", description = "Busca stock especifico por ID")    
    @GetMapping("/{id}")
    public ControlStock buscarControlStock(@PathVariable int id){
        return controlserv.getControlStocks(id);
    }

    @Operation(summary = "Actualizar la informacion de stock", description = "Actualiza los detalles del stock")    
    @PutMapping("/{id}")
    public ControlStock actualizaControlStock(@PathVariable int id, @RequestBody ControlStock controlStock){
        return controlserv.updateControlStock(controlStock);
    
    }

    @Operation(summary = "Eliminar stock por ID", description = "Elimina stock especifico por ID")    
    @DeleteMapping("/{id}")
    public String eliminarControl(@PathVariable int id){
        return controlserv.deleteControl(id);
    }

    @Operation(summary = "Contar el total de stock", description = "Devuelve la cantidad disponible de los productos")
    @GetMapping("/total")
    public int totalPerfumesv2(){
        return controlserv.totalControlesV2();
    }
}

