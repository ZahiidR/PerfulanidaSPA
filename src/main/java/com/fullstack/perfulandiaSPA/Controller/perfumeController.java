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



@RestController
@RequestMapping("http://10.155.64.162:8080/api/v1/perfumes")
public class perfumeController {
    @Autowired
    private perfumeService perfumeserv;

    @GetMapping
    public List<Perfumes> listarPerfumes(){
        return perfumeserv.getPerfumes();
    }

    @PostMapping
    public Perfumes agregarPerfume(@RequestBody Perfumes perfume){
        return perfumeserv.savePerfume(perfume);
    }

    @GetMapping("/{id}")
    public Perfumes buscarPerfumes(@PathVariable int id){
        return perfumeserv.getPerfumeId(id);
    }

    @PutMapping("/{id}")
    public Perfumes actualizaPerfumes(@PathVariable int id, @RequestBody Perfumes perfume){
        return perfumeserv.updatePerfume(perfume);
    }

    @DeleteMapping("/{id}")
    public String eliminarPerfume(@PathVariable int id){
        return perfumeserv.deletePerfume(id);
    }


    @GetMapping("/total")
    public int totalPerfumesv2(){
        return perfumeserv.totalPerfumesv2();
    }
}




    

