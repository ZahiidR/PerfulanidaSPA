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



@RestController
@RequestMapping("/api/v1/controlStock")
public class ControlStockController {
     @Autowired
    private ControlStockService controlserv;

    @GetMapping
    public List<ControlStock> listarControlStocks(){
        return controlserv.getControlStocks();
    }

    @PostMapping("{id}")
    public ControlStock agregarControlStock(@RequestBody ControlStock controlStock){
        return controlserv.saveControlStock(controlStock);
    }

    @GetMapping("/{id}")
    public ControlStock buscarControlStock(@PathVariable int id){
        return controlserv.getControlStocks(id);
    }

    @PutMapping("{id}")
    public ControlStock actualizaControlStock(@PathVariable int id, ControlStock controlStock){
        return controlserv.updateControlStock(controlStock);
    }

    @DeleteMapping("{id}")
    public String eliminarControl(@PathVariable int id){
        return controlserv.deleteControl(id);
    }


    @GetMapping("/total")
    public int totalPerfumesv2(){
        return controlserv.totalControlesV2();
    }
}

